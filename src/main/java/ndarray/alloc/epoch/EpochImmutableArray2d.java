package ndarray.alloc.epoch;

import ndarray.ImmutableArray2d;

/**
 * Date: 14.01.16
 * Time: 1:11
 *
 * @author Evgeny Antaev
 */
final class EpochImmutableArray2d extends EpochArray2d implements ImmutableArray2d {
    EpochImmutableArray2d(EpochArrayAllocator alloc, int offset, int numberOfRows, int numberOfColumns) {
        super(alloc, offset, numberOfRows, numberOfColumns);
    }
}
