package ndarray.fixed;

import java.util.function.IntToDoubleFunction;

/**
 * Date: 29.12.15
 * Time: 0:32
 *
 * @author Evgeny Antaev
 */
public interface MutableArray1d extends Array1d {
    MutableArray1d set(int i, double value);

    default MutableArray1d fill(IntToDoubleFunction generator) {
        for (int i = 0, len = length(); i < len; ++i) {
            set(i, generator.applyAsDouble(i));
        }
        return this;
    }
}
