package ndarray;

import ndarray.function.IntIntIntToDoubleFunction;

/**
 * Date: 01.01.16
 * Time: 13:19
 *
 * @author Evgeny Antaev
 */
public interface Array3dFactory<A extends Array3d> {

    A zeros(int numberOfItems, int numberOfRows, int numberOfColumns);

    A all(int numberOfItems, int numberOfRows, int numberOfColumns, double value);

    A copyOf(int numberOfItems, int numberOfRows, int numberOfColumns, double[] data);

    A wrap(int numberOfItems, int numberOfRows, int numberOfColumns, double[] data);

    A generate(int numberOfItems, int numberOfRows, int numberOfColumns, IntIntIntToDoubleFunction generator);
}
