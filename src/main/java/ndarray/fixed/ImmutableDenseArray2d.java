package ndarray.fixed;

import javax.annotation.Nonnull;
import java.util.Arrays;

import static java.util.Objects.requireNonNull;

/**
 * Date: 29.12.15
 * Time: 1:07
 *
 * @author Evgeny Antaev
 */
final class ImmutableDenseArray2d extends DenseArray2d implements ImmutableArray2d {

    ImmutableDenseArray2d(int numberOfRows, int numberOfColumns, double[] data) {
        super(numberOfRows, numberOfColumns, data);
    }

    @Override
    public ImmutableArray2d toImmutable() {
        return this;
    }

    @Override
    public MutableArray2d toMutable() {
        return FixedArrays.mutable2d.wrap(numberOfRows, numberOfColumns, Arrays.copyOf(data, data.length));
    }


    @Override
    public int readValues(@Nonnull double[] buffer) {
        requireNonNull(buffer, "buffer");
        System.arraycopy(data, 0, buffer, 0, data.length);
        return data.length;
    }
}
