package ndarray;

import java.util.Arrays;

import static java.util.Objects.requireNonNull;

/**
 * Date: 29.12.15
 * Time: 0:49
 *
 * @author Evgeny Antaev
 */
final class ImmutableDenseArray1d extends DenseArray1d implements ImmutableArray1d {

    public static ImmutableDenseArray1d copyOf(double[] data) {
        return wrap(Arrays.copyOf(requireNonNull(data, "data"), data.length));
    }

    public static ImmutableDenseArray1d wrap(double[] data) {
        return new ImmutableDenseArray1d(data);
    }

    private ImmutableDenseArray1d(double[] data) {
        super(data);
    }
}
