package ndarray;

import ndarray.function.IntIntIntToDoubleFunction;

import java.util.Arrays;

import static ndarray.NdArrayUtils.rangeCheck;

/**
 * Date: 31.12.15
 * Time: 0:39
 *
 * @author Evgeny Antaev
 */
final class ImmutableArray3dFactory implements Array3dFactory<ImmutableArray3d> {
    ImmutableArray3dFactory() {
    }

    @Override
    public ImmutableArray3d zeros(int numberOfItems, int numberOfRows, int numberOfColumns) {
        return new ImmutableZeroArray3d(numberOfItems, numberOfRows, numberOfColumns);
    }

    @Override
    public ImmutableArray3d all(int numberOfItems, int numberOfRows, int numberOfColumns, double value) {
        return new ImmutableAllTheSameArray3d(numberOfItems, numberOfRows, numberOfColumns, value);
    }

    @Override
    public ImmutableArray3d copyOf(int numberOfItems, int numberOfRows, int numberOfColumns, double[] data) {
        return wrap(numberOfItems, numberOfRows, numberOfColumns, Arrays.copyOf(data, data.length));
    }

    @Override
    public ImmutableArray3d wrap(int numberOfItems, int numberOfRows, int numberOfColumns, double[] data) {
        return new ImmutableDenseArray3d(numberOfItems, numberOfRows, numberOfColumns, data);
    }

    @Override
    public ImmutableArray3d generate(int numberOfItems, int numberOfRows, int numberOfColumns,
                                     IntIntIntToDoubleFunction generator) {
        if (numberOfRows < 0) {
            throw new IllegalArgumentException("numberOfRows should be >= 0");
        }
        if (numberOfColumns < 0) {
            throw new IllegalArgumentException("numberOfColumns should be >= 0");
        }
        double[] values = new double[numberOfItems * numberOfRows * numberOfColumns];
        int index = 0;
        for (int i = 0; i < numberOfItems; ++i) {
            for (int r = 0; r < numberOfRows; ++r) {
                for (int c = 0; c < numberOfColumns; ++c) {
                    values[index++] = generator.apply(i, r, c);
                }
            }
        }
        return wrap(numberOfItems, numberOfRows, numberOfColumns, values);
    }

    private static abstract class Array3dWithShape implements ImmutableArray3d, Array3d {
        private final int numberOfItems;
        private final int numberOfRows;
        private final int numberOfColumns;

        private Array3dWithShape(int numberOfItems, int numberOfRows, int numberOfColumns) {
            if (numberOfItems < 0) {
                throw new IllegalArgumentException("numberOfItems should be >= 0");
            }
            if (numberOfRows < 0) {
                throw new IllegalArgumentException("numberOfRows should be >= 0");
            }
            if (numberOfColumns < 0) {
                throw new IllegalArgumentException("numberOfColumns should be >= 0");
            }
            this.numberOfItems = numberOfItems;
            this.numberOfRows = numberOfRows;
            this.numberOfColumns = numberOfColumns;
        }

        @Override
        public int numberOfItems() {
            return numberOfItems;
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
        public final double at(int item, int row, int col) {
            rangeCheck(item, numberOfItems);
            rangeCheck(row, numberOfRows);
            rangeCheck(col, numberOfColumns);
            return atUnchecked(row, col);
        }
    }

    private static final class ImmutableZeroArray3d extends Array3dWithShape implements ImmutableArray3d {

        private ImmutableZeroArray3d(int numberOfItems, int numberOfRows, int numberOfColumns) {
            super(numberOfItems, numberOfRows, numberOfColumns);
        }

        @Override
        public double atUnchecked(int row, int col) {
            return 0;
        }

        @Override
        public ImmutableArray2d sliceAxis0(int item) {
            rangeCheck(item, numberOfItems());
            return NdArrays.immutable2d.zeros(numberOfRows(), numberOfColumns());
        }

        @Override
        public ImmutableArray2d sliceAxis1(int row) {
            rangeCheck(row, numberOfRows());
            return NdArrays.immutable2d.zeros(numberOfItems(), numberOfColumns());
        }

        @Override
        public ImmutableArray2d sliceAxis2(int col) {
            rangeCheck(col, numberOfColumns());
            return NdArrays.immutable2d.zeros(numberOfItems(), numberOfRows());
        }

        @Override
        public ImmutableArray3d toImmutable() {
            return this;
        }

        @Override
        public MutableArray3d toMutable() {
            return NdArrays.mutable3d.zeros(numberOfItems(), numberOfRows(), numberOfColumns());
        }
    }

    private static final class ImmutableAllTheSameArray3d extends Array3dWithShape {
        private final double value;

        private ImmutableAllTheSameArray3d(int numberOfItems, int numberOfRows, int numberOfColumns, double value) {
            super(numberOfItems, numberOfRows, numberOfColumns);
            this.value = value;
        }

        @Override
        public double atUnchecked(int row, int col) {
            return value;
        }

        @Override
        public ImmutableArray2d sliceAxis0(int item) {
            return NdArrays.immutable2d.all(numberOfRows(), numberOfColumns(), value);
        }

        @Override
        public ImmutableArray2d sliceAxis1(int row) {
            return NdArrays.immutable2d.all(numberOfItems(), numberOfColumns(), value);
        }

        @Override
        public ImmutableArray2d sliceAxis2(int col) {
            return NdArrays.immutable2d.all(numberOfItems(), numberOfRows(), value);
        }

        @Override
        public ImmutableArray3d toImmutable() {
            return this;
        }

        @Override
        public MutableArray3d toMutable() {
            return NdArrays.mutable3d.all(numberOfItems(), numberOfRows(), numberOfColumns(), value);
        }
    }
}
