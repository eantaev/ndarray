package ndarray;

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;
import static ndarray.NdArrayUtils.rangeCheck;

/**
 * Date: 02.01.16
 * Time: 18:56
 *
 * @author Evgeny Antaev
 */
abstract class DenseArray3d implements Array3d {
    final int numberOfItems;
    final int numberOfRows;
    final int numberOfColumns;
    final double[] data;

    DenseArray3d(int numberOfItems, int numberOfRows, int numberOfColumns) {
        if (numberOfItems < 0) {
            throw new IllegalArgumentException("numberOfItems should be >= 0");
        }
        if (numberOfRows < 0) {
            throw new IllegalArgumentException("numberOfRows should be >= 0");
        }
        if (numberOfColumns < 0) {
            throw new IllegalArgumentException("numberOfColumns should be >= 0");
        }
        this.numberOfItems = numberOfItems;
        this.numberOfRows = numberOfRows;
        this.numberOfColumns = numberOfColumns;
        this.data = new double[numberOfItems * numberOfRows * numberOfColumns];
    }

    DenseArray3d(int numberOfItems, int numberOfRows, int numberOfColumns, double[] data) {
        if (numberOfItems < 0) {
            throw new IllegalArgumentException("numberOfItems should be >= 0");
        }
        if (numberOfRows < 0) {
            throw new IllegalArgumentException("numberOfRows should be >= 0");
        }
        if (numberOfColumns < 0) {
            throw new IllegalArgumentException("numberOfColumns should be >= 0");
        }
        requireNonNull(data, "data");
        if (data.length != numberOfItems * numberOfRows * numberOfColumns) {
            throw new IllegalArgumentException(format("(data.length = %d) != (items * rows * columns = %d)",
                data.length, numberOfItems * numberOfRows * numberOfColumns));
        }
        this.numberOfItems = numberOfItems;
        this.numberOfRows = numberOfRows;
        this.numberOfColumns = numberOfColumns;
        this.data = data;
    }

    public int numberOfItems() {
        return numberOfItems;
    }

    public int numberOfRows() {
        return numberOfRows;
    }

    public int numberOfColumns() {
        return numberOfColumns;
    }

    @Override
    public final double at(int item, int row, int col) {
        return data[dataIndex(item, row, col)];
    }

    int dataIndex(int item, int row, int col) {
        rangeCheck(item, numberOfItems);
        rangeCheck(row, numberOfRows);
        rangeCheck(col, numberOfColumns);
        return item * (numberOfRows * numberOfColumns) + row * numberOfColumns + col;
    }
}
