package ndarray;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.function.IntToDoubleFunction;

import static java.util.Objects.requireNonNull;

/**
 * Date: 31.12.15
 * Time: 0:54
 *
 * @author Evgeny Antaev
 */
final class MutableArray1dFactory implements Array1dFactory<MutableArray1d> {
    MutableArray1dFactory() {
    }

    @Override
    public MutableArray1d zeros(int length) {
        if (length < 0) {
            throw new IllegalArgumentException("length < 0: " + length);
        }
        return wrap(new double[length]);
    }

    @Override
    public MutableArray1d all(int length, double value) {
        return null;
    }

    @Override
    public MutableArray1d copyOf(double... values) {
        return wrap(Arrays.copyOf(values, values.length));
    }

    @Override
    public MutableArray1d wrap(double... values) {
        return new MutableDenseArray1d(values);
    }

    @Override
    public MutableArray1d generateEagerly(int length, @Nonnull IntToDoubleFunction generator) {
        return zeros(length).fill(requireNonNull(generator, "generator"));
    }
}
