package ndarray;

import javax.annotation.Nonnull;
import java.util.function.IntToDoubleFunction;

/**
 * Date: 01.01.16
 * Time: 12:59
 *
 * @author Evgeny Antaev
 */
public interface Array1dFactory<A extends Array1d> {

    A zeros(int length);

    A all(int length, double value);

    A copyOf(double... values);

    A wrap(double... values);

    A generateEagerly(int length, @Nonnull IntToDoubleFunction generator);
}
