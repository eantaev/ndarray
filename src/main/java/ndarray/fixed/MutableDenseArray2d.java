package ndarray.fixed;

import java.util.Arrays;

/**
 * Date: 29.12.15
 * Time: 1:03
 *
 * @author Evgeny Antaev
 */
final class MutableDenseArray2d extends DenseArray2d implements MutableArray2d {
    MutableDenseArray2d(int numberOfRows, int numberOfColumns) {
        super(numberOfRows, numberOfColumns);
    }

    MutableDenseArray2d(int numberOfRows, int numberOfColumns, double[] data) {
        super(numberOfRows, numberOfColumns, data);
    }

    @Override
    public MutableArray2d set(int row, int col, double value) {
        this.data[dataIndex(row, col)] = value;
        return this;
    }

    public ImmutableArray2d toImmutable() {
        return FixedArrays.immutable2d.wrap(numberOfRows, numberOfColumns, Arrays.copyOf(data, data.length));
    }

    @Override
    public MutableArray2d toMutable() {
        return this;
    }
}
