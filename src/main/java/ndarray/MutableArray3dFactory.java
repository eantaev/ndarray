package ndarray;

import ndarray.function.IntIntIntToDoubleFunction;

import java.util.Arrays;

/**
 * Date: 31.12.15
 * Time: 0:41
 *
 * @author Evgeny Antaev
 */
final class MutableArray3dFactory implements Array3dFactory<MutableArray3d> {
    MutableArray3dFactory() {
    }

    @Override
    public MutableArray3d zeros(int numberOfItems, int numberOfRows, int numberOfColumns) {
        return new MutableDenseArray3d(numberOfItems, numberOfRows, numberOfColumns);
    }

    @Override
    public MutableArray3d all(int numberOfItems, int numberOfRows, int numberOfColumns, double value) {
        if (numberOfRows < 0) {
            throw new IllegalArgumentException("numberOfRows should be >= 0");
        }
        if (numberOfColumns < 0) {
            throw new IllegalArgumentException("numberOfColumns should be >= 0");
        }
        double[] values = new double[numberOfRows * numberOfColumns];
        Arrays.fill(values, value);
        return wrap(numberOfItems, numberOfRows, numberOfColumns, values);
    }

    @Override
    public MutableArray3d copyOf(int numberOfItems, int numberOfRows, int numberOfColumns, double[] data) {
        return wrap(numberOfItems, numberOfRows, numberOfColumns, Arrays.copyOf(data, data.length));
    }

    @Override
    public MutableArray3d wrap(int numberOfItems, int numberOfRows, int numberOfColumns, double[] data) {
        return new MutableDenseArray3d(numberOfItems, numberOfRows, numberOfColumns, data);
    }

    @Override
    public MutableArray3d generate(int numberOfItems, int numberOfRows, int numberOfColumns,
                                   IntIntIntToDoubleFunction generator) {
        return zeros(numberOfItems, numberOfRows, numberOfColumns).fill(generator);
    }
}
