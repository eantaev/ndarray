package ndarray.alloc;

import ndarray.Array1dFactory;
import ndarray.ImmutableArray1d;
import ndarray.MutableArray1d;

/**
 * Date: 13.01.16
 * Time: 23:57
 *
 * @author Evgeny Antaev
 */
public interface ArrayAllocator {

    void newEpoch();

    Array1dFactory<ImmutableArray1d> immutable1d();

    Array1dFactory<MutableArray1d> mutable1d();
}
