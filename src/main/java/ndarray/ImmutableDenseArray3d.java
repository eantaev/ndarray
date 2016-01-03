package ndarray;

import javax.annotation.Nonnull;
import java.util.Arrays;

import static java.util.Objects.requireNonNull;

/**
 * Date: 29.12.15
 * Time: 1:07
 *
 * @author Evgeny Antaev
 */
final class ImmutableDenseArray3d extends DenseArray3d implements ImmutableArray3d {

    ImmutableDenseArray3d(int numberOfItems, int numberOfRows, int numberOfColumns, @Nonnull double[] data) {
        super(numberOfItems, numberOfRows, numberOfColumns, data);
    }

    @Override
    public ImmutableArray3d toImmutable() {
        return this;
    }

    @Override
    public MutableArray3d toMutable() {
        return NdArrays.mutable3d.wrap(numberOfItems, numberOfRows, numberOfColumns, Arrays.copyOf(data, data.length));
    }


    @Override
    public int readValues(@Nonnull double[] buffer) {
        requireNonNull(buffer, "buffer");
        System.arraycopy(data, 0, buffer, 0, data.length);
        return data.length;
    }
}
