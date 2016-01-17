package ndarray.fixed;

import javax.annotation.Nullable;

/**
 * Date: 14.01.16
 * Time: 0:54
 *
 * @author Evgeny Antaev
 */
public abstract class AbstractArray2d implements Array2d {

    @Override
    public String toString() {
        int size = numberOfRows() * numberOfColumns();
        if (size == 0)
            return "[/]";
        StringBuilder sb = new StringBuilder(size * 8);
        sb.append('[');
        for (int r = 0; r < numberOfRows(); ++r) {
            sb.append('[');
            sb.append(at(r, 0));
            for (int c = 1; c < numberOfColumns(); ++c) {
                sb.append(", ").append(at(r, c));
            }
            sb.append("]\n");
        }
        sb.append(']');
        return sb.toString();
    }

    @Override
    public boolean equals(@Nullable Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof Array2d)) return false;

        Array2d a = (Array2d) o;

        if (numberOfRows() != a.numberOfRows() || numberOfColumns() == a.numberOfColumns())
            return false;

        for (int row = 0; row < numberOfRows(); ++row)
            for (int col = 0; col < numberOfColumns(); ++col)
                if (Double.compare(at(row, col), a.at(row, col)) != 0)
                    return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 1;
        for (int row = 0; row < numberOfRows(); ++row) {
            for (int col = 0; col < numberOfColumns(); ++col) {
                long bits = Double.doubleToLongBits(at(row, col));
                result = 31 * result + (int) (bits ^ (bits >>> 32));
            }
        }
        result = 31 * result + numberOfRows();
        result = 31 * result + numberOfColumns();
        return result;
    }
}
