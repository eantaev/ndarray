package ndarray.fixed;

import ndarray.fixed.function.IntIntIntToDoubleFunction;

import static ndarray.NdArrayUtils.rangeCheck;

/**
 * Date: 02.01.16
 * Time: 18:31
 *
 * @author Evgeny Antaev
 */
public interface MutableArray3d extends Array3d {

    MutableArray3d set(int i, int j, int k, double value);

    default MutableArray3d fill(IntIntIntToDoubleFunction generator) {
        for (int i = 0; i < numberOfItems(); ++i) {
            for (int r = 0; r < numberOfRows(); ++r) {
                for (int c = 0; c < numberOfColumns(); ++c) {
                    set(i, r, c, generator.apply(i, r, c));
                }
            }
        }
        return this;
    }

    default MutableArray2d sliceAxis0(int item) {
        rangeCheck(item, numberOfItems());
        return new MutableArray3dAxis0SliceView(this, item);
    }

    default MutableArray2d sliceAxis1(int row) {
        rangeCheck(row, numberOfRows());
        return new MutableArray3dAxis1SliceView(this, row);
    }

    default MutableArray2d sliceAxis2(int col) {
        rangeCheck(col, numberOfColumns());
        return new MutableArray3dAxis2SliceView(this, col);
    }
}
