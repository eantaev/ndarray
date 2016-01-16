package ndarray.alloc.epoch;

import ndarray.MutableArray2d;

import static ndarray.NdArrayUtils.rangeCheck;

/**
 * Date: 14.01.16
 * Time: 1:14
 *
 * @author Evgeny Antaev
 */
final class EpochMutableArray2d extends EpochArray2d implements MutableArray2d {
    EpochMutableArray2d(EpochArrayAllocator alloc, int offset, int numberOfRows, int numberOfColumns) {
        super(alloc, offset, numberOfRows, numberOfColumns);
    }

    @Override
    public MutableArray2d set(int row, int col, double value) {
        checkEpoch();
        rangeCheck(row, numberOfRows);
        rangeCheck(col, numberOfColumns);
        alloc.set(dataIndex(row, col), value);
        return this;
    }
}
