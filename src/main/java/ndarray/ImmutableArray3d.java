package ndarray;

import static ndarray.NdArrayUtils.rangeCheck;

/**
 * Date: 02.01.16
 * Time: 18:30
 *
 * @author Evgeny Antaev
 */
public interface ImmutableArray3d extends Array3d {

    default ImmutableArray2d sliceAxis0(int item) {
        rangeCheck(item, numberOfItems());
        return new ImmutableArray3dAxis0SliceView(this, item);
    }

    default ImmutableArray2d sliceAxis1(int row) {
        rangeCheck(row, numberOfRows());
        return new ImmutableArray3dAxis1SliceView(this, row);
    }

    default ImmutableArray2d sliceAxis2(int col) {
        rangeCheck(col, numberOfColumns());
        return new ImmutableArray3dAxis2SliceView(this, col);
    }
}
