package ndarray.alloc.epoch;

import ndarray.AbstractArray2d;

import static java.util.Objects.requireNonNull;
import static ndarray.NdArrayUtils.rangeCheck;

/**
 * Date: 14.01.16
 * Time: 1:05
 *
 * @author Evgeny Antaev
 */
abstract class EpochArray2d extends AbstractArray2d {
    final EpochArrayAllocator alloc;
    final int epoch;
    final int offset;
    final int numberOfRows;
    final int numberOfColumns;

    EpochArray2d(EpochArrayAllocator alloc, int offset, int numberOfRows, int numberOfColumns) {
        this.alloc = requireNonNull(alloc);
        this.epoch = alloc.epoch;
        this.offset = offset;

        if (numberOfRows < 0) {
            throw new IllegalArgumentException("numberOfRows should be >= 0");
        }
        if (numberOfColumns < 0) {
            throw new IllegalArgumentException("numberOfColumns should be >= 0");
        }
        this.numberOfRows = numberOfRows;
        this.numberOfColumns = numberOfColumns;
    }

    @Override
    public final double at(int row, int col) {
        return alloc.get(dataIndex(row, col));
    }

    @Override
    public boolean isZero() {
        return false;
    }

    int dataIndex(int row, int col) {
        rangeCheck(row, numberOfRows());
        rangeCheck(col, numberOfColumns());
        return offset + row * numberOfColumns + col;
    }

    void checkEpoch() {
        if (this.epoch != alloc.epoch)
            throw new IllegalStateException("Epoch has changed. This object is no longer valid.");
    }

    public int numberOfRows() {
        return numberOfRows;
    }

    public int numberOfColumns() {
        return numberOfColumns;
    }
}
