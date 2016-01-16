package ndarray;

/**
 * Date: 03.01.16
 * Time: 13:32
 *
 * @author Evgeny Antaev
 */
abstract class Array3dAxis0SliceView extends Array3dAxisSliceView {

    Array3dAxis0SliceView(int itemIndex) {
        super(itemIndex);
    }

    @Override
    public int numberOfRows() {
        return owner().numberOfRows();
    }

    @Override
    public int numberOfColumns() {
        return owner().numberOfColumns();
    }

    @Override
    public double at(int row, int col) {
        return owner().at(sliceIndex, row, col);
    }

}
