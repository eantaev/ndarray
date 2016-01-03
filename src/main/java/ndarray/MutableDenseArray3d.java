package ndarray;

import javax.annotation.Nonnull;
import java.util.Arrays;

/**
 * Date: 29.12.15
 * Time: 1:03
 *
 * @author Evgeny Antaev
 */
final class MutableDenseArray3d extends DenseArray3d implements MutableArray3d {
    MutableDenseArray3d(int numberOfItems, int numberOfRows, int numberOfColumns) {
        super(numberOfItems, numberOfRows, numberOfColumns);
    }

    MutableDenseArray3d(int numberOfItems, int numberOfRows, int numberOfColumns, @Nonnull double[] data) {
        super(numberOfItems, numberOfRows, numberOfColumns, data);
    }

    @Override
    public MutableArray3d set(int item, int row, int col, double value) {
        this.data[dataIndex(item, row, col)] = value;
        return this;
    }

    @Override
    public ImmutableArray3d toImmutable() {
        return NdArrays.immutable3d.wrap(
            numberOfItems, numberOfRows, numberOfColumns, Arrays.copyOf(data, data.length));
    }

    @Override
    public MutableArray3d toMutable() {
        return this;
    }
}
