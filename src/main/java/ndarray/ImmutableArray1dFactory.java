package ndarray;

import java.util.Arrays;
import java.util.function.IntToDoubleFunction;

import static ndarray.NdArrayUtils.rangeCheck;

/**
 * Date: 31.12.15
 * Time: 0:12
 *
 * @author Evgeny Antaev
 */
final class ImmutableArray1dFactory implements Array1dFactory<ImmutableArray1d> {
    ImmutableArray1dFactory() {
    }

    public ImmutableArray1d zeros(int length) {
        return new ImmutableZeroArray1d(length);
    }

    @Override
    public ImmutableArray1d all(int length, double value) {
        return new ImmutableAllTheSameArray1d(length, value);
    }

    public ImmutableArray1d copyOf(double... data) {
        return ImmutableDenseArray1d.copyOf(data);
    }

    public ImmutableArray1d wrap(double... data) {
        return ImmutableDenseArray1d.wrap(data);
    }

    @Override
    public ImmutableArray1d generateEagerly(int length, IntToDoubleFunction generator) {
        if (length < 0) {
            throw new IllegalArgumentException("length < 0");
        }
        double[] values = new double[length];
        Arrays.setAll(values, generator);
        return wrap(values);
    }

    private static abstract class Array1dWithLength implements Array1d {
        private final int length;

        protected Array1dWithLength(int length) {
            if (length < 0) {
                throw new IllegalArgumentException("length < 0. " + length);
            }
            this.length = length;
        }

        @Override
        public int length() {
            return length;
        }

        abstract double atUnchecked(int index);

        @Override
        public final double at(int index) {
            rangeCheck(index, length());
            return atUnchecked(index);
        }
    }

    private static final class ImmutableZeroArray1d extends Array1dWithLength implements ImmutableArray1d {
        private ImmutableZeroArray1d(int length) {
            super(length);
        }

        @Override
        public double atUnchecked(int index) {
            return 0;
        }
    }

    private static final class ImmutableAllTheSameArray1d extends Array1dWithLength implements ImmutableArray1d {
        private final double value;

        private ImmutableAllTheSameArray1d(int length, double value) {
            super(length);
            this.value = value;
        }

        @Override
        public double atUnchecked(int index) {
            return value;
        }
    }
}
