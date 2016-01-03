package ndarray;

import ndarray.function.IntIntToDoubleFunction;

import javax.annotation.Nonnull;

/**
 * Date: 01.01.16
 * Time: 13:19
 *
 * @author Evgeny Antaev
 */
public interface Array2dFactory<A extends Array2d> {

    A zeros(int numberOfRows, int numberOfColumns);

    A all(int numberOfRows, int numberOfColumns, double value);

    A copyOf(int numberOfRows, int numberOfColumns, @Nonnull double[] data);

    A wrap(int numberOfRows, int numberOfColumns, @Nonnull double[] data);

    A generate(int numberOfRows, int numberOfColumns, @Nonnull IntIntToDoubleFunction generator);
}
