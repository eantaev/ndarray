package ndarray;

import ndarray.function.IntIntToDoubleFunction;

import java.util.Arrays;

/**
 * Date: 31.12.15
 * Time: 0:41
 *
 * @author Evgeny Antaev
 */
final class MutableArray2dFactory implements Array2dFactory<MutableArray2d> {
    MutableArray2dFactory() {
    }

    @Override
    public MutableArray2d zeros(int numberOfRows, int numberOfColumns) {
        return new MutableDenseArray2d(numberOfRows, numberOfColumns);
    }

    @Override
    public MutableArray2d all(int numberOfRows, int numberOfColumns, double value) {
        if (numberOfRows < 0) {
            throw new IllegalArgumentException("numberOfRows should be >= 0");
        }
        if (numberOfColumns < 0) {
            throw new IllegalArgumentException("numberOfColumns should be >= 0");
        }
        double[] values = new double[numberOfRows * numberOfColumns];
        Arrays.fill(values, value);
        return wrap(numberOfRows, numberOfColumns, values);
    }

    @Override
    public MutableArray2d copyOf(int numberOfRows, int numberOfColumns, double[] data) {
        return wrap(numberOfRows, numberOfColumns, Arrays.copyOf(data, data.length));
    }

    @Override
    public MutableArray2d wrap(int numberOfRows, int numberOfColumns, double[] data) {
        return new MutableDenseArray2d(numberOfRows, numberOfColumns, data);
    }

    @Override
    public MutableArray2d generate(int numberOfRows, int numberOfColumns, IntIntToDoubleFunction generator) {
        return zeros(numberOfRows, numberOfColumns).fill(generator);
    }
}
