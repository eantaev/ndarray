package ndarray.alloc.epoch;

import ndarray.MutableArray1d;

import static ndarray.NdArrayUtils.rangeCheck;

/**
 * Date: 14.01.16
 * Time: 0:44
 *
 * @author Evgeny Antaev
 */
final class EpochMutableArray1d extends EpochArray1d implements MutableArray1d {

    EpochMutableArray1d(EpochArrayAllocator alloc, int offset, int length) {
        super(alloc, offset, length);
    }

    @Override
    public MutableArray1d set(int i, double value) {
        checkEpoch();
        rangeCheck(i, length);
        alloc.set(dataIndex(i), value);
        return this;
    }
}
