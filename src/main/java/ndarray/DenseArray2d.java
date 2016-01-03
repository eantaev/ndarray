package ndarray;

import javax.annotation.Nullable;
import java.util.Arrays;

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;
import static ndarray.NdArrayUtils.rangeCheck;

/**
 * Date: 29.12.15
 * Time: 0:36
 *
 * @author Evgeny Antaev
 */
abstract class DenseArray2d implements Array2d {
    final int numberOfRows;
    final int numberOfColumns;
    final double[] data;

    DenseArray2d(int numberOfRows, int numberOfColumns) {
        if (numberOfRows < 0) {
            throw new IllegalArgumentException("numberOfRows should be >= 0");
        }
        if (numberOfColumns < 0) {
            throw new IllegalArgumentException("numberOfColumns should be >= 0");
        }
        this.numberOfRows = numberOfRows;
        this.numberOfColumns = numberOfColumns;
        this.data = new double[numberOfRows * numberOfColumns];
    }

    DenseArray2d(int numberOfRows, int numberOfColumns, double[] data) {
        if (numberOfRows < 0) {
            throw new IllegalArgumentException("numberOfRows should be >= 0");
        }
        if (numberOfColumns < 0) {
            throw new IllegalArgumentException("numberOfColumns should be >= 0");
        }
        requireNonNull(data, "data");
        if (data.length != numberOfRows * numberOfColumns) {
            throw new IllegalArgumentException(format("(data.length = %d) != (numberOfRows * numberOfColumns = %d",
                data.length, numberOfRows * numberOfColumns));
        }
        this.numberOfRows = numberOfRows;
        this.numberOfColumns = numberOfColumns;
        this.data = data;
    }

    @Override
    public final double at(int row, int col) {
        return data[dataIndex(row, col)];
    }

    int dataIndex(int row, int col) {
        rangeCheck(row, numberOfRows());
        rangeCheck(col, numberOfColumns());
        return row * numberOfColumns + col;
    }

    public int numberOfRows() {
        return numberOfRows;
    }

    public int numberOfColumns() {
        return numberOfColumns;
    }

    @Override
    public String toString() {
        if (data.length == 0)
            return "[/]";
        StringBuilder sb = new StringBuilder(numberOfRows * numberOfColumns * 8);
        sb.append('[');
        for (int r = 0; r < numberOfRows; ++r) {
            sb.append('[');
            sb.append(at(r, 0));
            for (int c = 1; c < numberOfColumns; ++c) {
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
        if (o == null || getClass() != o.getClass()) return false;

        DenseArray2d that = (DenseArray2d) o;

        return numberOfRows == that.numberOfRows
            && numberOfColumns == that.numberOfColumns
            && Arrays.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        int result = numberOfRows;
        result = 31 * result + numberOfColumns;
        result = 31 * result + Arrays.hashCode(data);
        return result;
    }
}
