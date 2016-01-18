package ndarray.pos;

import ndarray.Position;

/**
 * Date: 19.01.16
 * Time: 1:01
 *
 * @author Evgeny Antaev
 */
abstract class AbstractPosition implements Position {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof Position)) return false;

        Position that = (Position) o;

        int numberOfAxes = numberOfAxes();

        if (numberOfAxes != that.numberOfAxes()) return false;

        for (int axis = 0; axis < numberOfAxes; ++axis) {
            if (coordinate(axis) != that.coordinate(axis)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = 1;
        int numberOfAxes = numberOfAxes();
        for (int axis = 0; axis < numberOfAxes; ++axis) {
            result = 31 * result + coordinate(axis);
        }
        return result;
    }

    @Override
    public String toString() {
        int numberOfAxes = numberOfAxes();
        if (numberOfAxes == 0) {
            return "Pos[/]";
        }
        StringBuilder sb = new StringBuilder(5 + 4 * numberOfAxes);
        sb.append("Pos[");
        sb.append(coordinate(0));
        for (int axis = 1; axis < numberOfAxes; ++axis) {
            sb.append(", ");
            sb.append(coordinate(axis));
        }
        sb.append(']');
        return sb.toString();
    }
}
