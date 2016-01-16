package ndarray.alloc.epoch;

import ndarray.ImmutableArray1d;

/**
 * Date: 14.01.16
 * Time: 0:43
 *
 * @author Evgeny Antaev
 */
final class EpochImmutableArray1d extends EpochArray1d implements ImmutableArray1d {
    EpochImmutableArray1d(EpochArrayAllocator alloc, int offset, int length) {
        super(alloc, offset, length);
    }
}
