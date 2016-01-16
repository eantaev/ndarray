package ndarray;

/**
 * Date: 03.01.16
 * Time: 13:34
 *
 * @author Evgeny Antaev
 */
abstract class Array3dAxis1SliceView extends Array3dAxisSliceView {

    Array3dAxis1SliceView(int rowIndex) {
        super(rowIndex);
    }

    @Override
    public int numberOfRows() {
        return owner().numberOfItems();
    }

    @Override
    public int numberOfColumns() {
        return owner().numberOfColumns();
    }

    @Override
    public double at(int row, int col) {
        return owner().at(row, sliceIndex, col);
    }
}
