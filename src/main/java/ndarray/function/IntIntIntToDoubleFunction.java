package ndarray.function;

/**
 * Date: 01.01.16
 * Time: 17:05
 *
 * @author Evgeny Antaev
 */
@FunctionalInterface
public interface IntIntIntToDoubleFunction {
    double apply(int a, int b, int c);
}
