package ndarray.pos;

import ndarray.Slider;

import javax.annotation.Nonnull;
import java.util.Arrays;

import static java.util.Objects.requireNonNull;
import static ndarray.NdArrayUtils.*;

/**
 * Date: 19.01.16
 * Time: 1:01
 *
 * @author Evgeny Antaev
 */
final class DefaultPosition extends AbstractPosition implements Slider {
    private final int[] coordinates;

    static DefaultPosition wrap(int[] coordinates) {
        requireNonNull(coordinates, "coordinates");
        if (coordinates.length == 0) {
            throwIllegalArgument("Coordinates shouldn't be empty");
        }
        if (!allNonNegative(coordinates)) {
            throwIllegalArgument("Coordinates should be >= 0. Got: " + Arrays.toString(coordinates));
        }
        return new DefaultPosition(coordinates);
    }

    DefaultPosition(int[] coordinates) {
        this.coordinates = coordinates;
    }

    @Override
    public int numberOfAxes() {
        return coordinates.length;
    }

    @Override
    public int coordinate(int axis) {
        rangeCheck(axis, numberOfAxes());
        return coordinates[axis];
    }

    @Nonnull
    @Override
    public Slider move(int axis, int coordinate) {
        coordinates[axis] = coordinate;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;

        return o instanceof DefaultPosition
            ? Arrays.equals(coordinates, ((DefaultPosition) o).coordinates)
            : super.equals(o);

    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(coordinates);
    }

    @Override
    public String toString() {
        return "Pos" + Arrays.toString(coordinates);
    }
}
