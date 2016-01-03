package ndarray;

import ndarray.function.IntIntToDoubleFunction;

import static ndarray.NdArrayUtils.rangeCheck;

/**
 * Date: 29.12.15
 * Time: 0:30
 *
 * @author Evgeny Antaev
 */
public interface MutableArray2d extends Array2d {

    MutableArray2d set(int row, int col, double value);

    default MutableArray1d sliceAxis0(int row) {
        rangeCheck(row, numberOfRows());
        return new MutableArray2dRowView(this, row);
    }

    default MutableArray1d sliceAxis1(int col) {
        rangeCheck(col, numberOfColumns());
        return new MutableArray2dColumnView(this, col);
    }

    default MutableArray2d fill(IntIntToDoubleFunction generator) {
        for (int r = 0; r < numberOfRows(); ++r) {
            for (int c = 0; c < numberOfColumns(); ++c) {
                set(r, c, generator.apply(r, c));
            }
        }
        return this;
    }
}
