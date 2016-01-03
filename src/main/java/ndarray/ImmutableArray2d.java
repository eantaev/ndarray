package ndarray;

import static ndarray.NdArrayUtils.rangeCheck;

/**
 * Date: 29.12.15
 * Time: 1:08
 *
 * @author Evgeny Antaev
 */
public interface ImmutableArray2d extends Array2d {
    default ImmutableArray1d sliceAxis0(int row) {
        rangeCheck(row, numberOfRows());
        return new ImmutableArray2dRowView(this, row);
    }

    default ImmutableArray1d sliceAxis1(int col) {
        rangeCheck(col, numberOfColumns());
        return new ImmutableArray2dColumnView(this, col);
    }

}
