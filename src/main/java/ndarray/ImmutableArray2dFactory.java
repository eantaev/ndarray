package ndarray;

import ndarray.function.IntIntToDoubleFunction;

import java.util.Arrays;

import static ndarray.NdArrayUtils.rangeCheck;

/**
 * Date: 31.12.15
 * Time: 0:39
 *
 * @author Evgeny Antaev
 */
final class ImmutableArray2dFactory implements Array2dFactory<ImmutableArray2d> {
    ImmutableArray2dFactory() {
    }

    @Override
    public ImmutableArray2d zeros(int numberOfRows, int numberOfColumns) {
        return new ImmutableZeroArray2d(numberOfRows, numberOfColumns);
    }

    @Override
    public ImmutableArray2d all(int numberOfRows, int numberOfColumns, double value) {
        return new ImmutableAllTheSameArray2d(numberOfRows, numberOfColumns, value);
    }

    @Override
    public ImmutableArray2d copyOf(int numberOfRows, int numberOfColumns, double[] data) {
        return wrap(numberOfRows, numberOfColumns, Arrays.copyOf(data, data.length));
    }

    @Override
    public ImmutableArray2d wrap(int numberOfRows, int numberOfColumns, double[] data) {
        return new ImmutableDenseArray2d(numberOfRows, numberOfColumns, data);
    }

    @Override
    public ImmutableArray2d generate(int numberOfRows, int numberOfColumns, IntIntToDoubleFunction generator) {
        if (numberOfRows < 0) {
            throw new IllegalArgumentException("numberOfRows should be >= 0");
        }
        if (numberOfColumns < 0) {
            throw new IllegalArgumentException("numberOfColumns should be >= 0");
        }
        double[] values = new double[numberOfRows * numberOfColumns];
        int index = 0;
        for (int r = 0; r < numberOfRows; ++r) {
            for (int c = 0; c < numberOfColumns; ++c) {
                values[index++] = generator.apply(r, c);
            }
        }
        return wrap(numberOfRows, numberOfColumns, values);
    }

    private static abstract class Array2dWithShape implements ImmutableArray2d, Array2d {
        private final int numberOfRows;
        private final int numberOfColumns;

        private Array2dWithShape(int numberOfRows, int numberOfColumns) {
            if (numberOfRows < 0) {
                throw new IllegalArgumentException("numberOfRows should be >= 0");
            }
            if (numberOfColumns < 0) {
                throw new IllegalArgumentException("numberOfColumns should be >= 0");
            }
            this.numberOfRows = numberOfRows;
            this.numberOfColumns = numberOfColumns;
        }

        @Override
        public int numberOfRows() {
            return numberOfRows;
        }

        @Override
        public int numberOfColumns() {
            return numberOfColumns;
        }

        protected abstract double atUnchecked(int row, int col);

        @Override
        public final double at(int row, int col) {
            rangeCheck(row, numberOfRows());
            rangeCheck(col, numberOfColumns());
            return atUnchecked(row, col);
        }
    }

    private static final class ImmutableZeroArray2d extends Array2dWithShape implements ImmutableArray2d {

        private ImmutableZeroArray2d(int numberOfRows, int numberOfColumns) {
            super(numberOfRows, numberOfColumns);
        }

        @Override
        public double atUnchecked(int row, int col) {
            return 0;
        }

        @Override
        public ImmutableArray1d sliceAxis0(int row) {
            rangeCheck(row, numberOfRows());
            return NdArrays.immutable1d.zeros(numberOfColumns());
        }

        @Override
        public ImmutableArray1d sliceAxis1(int col) {
            rangeCheck(col, numberOfColumns());
            return NdArrays.immutable1d.zeros(numberOfRows());
        }

        @Override
        public MutableArray2d toMutable() {
            return NdArrays.mutable2d.zeros(numberOfRows(), numberOfColumns());
        }
    }

    private static final class ImmutableAllTheSameArray2d extends Array2dWithShape {
        private final double value;

        private ImmutableAllTheSameArray2d(int numberOfRows, int numberOfColumns, double value) {
            super(numberOfRows, numberOfColumns);
            this.value = value;
        }

        @Override
        public double atUnchecked(int row, int col) {
            return value;
        }

        @Override
        public ImmutableArray1d sliceAxis0(int row) {
            return NdArrays.immutable1d.all(numberOfColumns(), value);
        }

        @Override
        public ImmutableArray1d sliceAxis1(int col) {
            return NdArrays.immutable1d.all(numberOfRows(), value);
        }

        @Override
        public MutableArray2d toMutable() {
            return NdArrays.mutable2d.all(numberOfRows(), numberOfColumns(), value);
        }
    }
}
