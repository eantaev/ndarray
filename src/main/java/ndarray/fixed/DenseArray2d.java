package ndarray.fixed;

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
abstract class DenseArray2d extends AbstractArray2d {
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

    @Override
    public boolean isZero() {
        return false;
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
    public int hashCode() {
        int result = Arrays.hashCode(data);
        result = 31 * result + numberOfRows;
        result = 31 * result + numberOfColumns;
        return result;
    }
}
