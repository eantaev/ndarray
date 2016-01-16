package ndarray;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.function.IntToDoubleFunction;

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
    public ImmutableArray1d generate(int length, @Nonnull IntToDoubleFunction generator) {
        if (length < 0) {
            throw new IllegalArgumentException("length < 0");
        }
        double[] values = new double[length];
        Arrays.setAll(values, generator);
        return wrap(values);
    }

    private static abstract class Array1dWithLength extends AbstractArray1d implements ImmutableArray1d {
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
    }

    private static final class ImmutableZeroArray1d extends Array1dWithLength {
        private ImmutableZeroArray1d(int length) {
            super(length);
        }

        @Override
        public double atUnchecked(int index) {
            return 0;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder(length() * 5);
            sb.append("[0.0");
            for (int i = 1; i < length(); ++i) {
                sb.append(", 0.0");
            }
            sb.append(']');
            return sb.toString();
        }

        @Override
        public boolean isZero() {
            return true;
        }
    }

    private static final class ImmutableAllTheSameArray1d extends Array1dWithLength {
        private final double value;

        private ImmutableAllTheSameArray1d(int length, double value) {
            super(length);
            this.value = value;
        }

        @Override
        public double atUnchecked(int index) {
            return value;
        }

        @Override
        public boolean isZero() {
            return value == 0d;
        }
    }
}
