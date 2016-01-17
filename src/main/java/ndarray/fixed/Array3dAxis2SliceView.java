package ndarray.fixed;

/**
 * Date: 03.01.16
 * Time: 13:34
 *
 * @author Evgeny Antaev
 */
abstract class Array3dAxis2SliceView extends Array3dAxisSliceView {

    Array3dAxis2SliceView(int colIndex) {
        super(colIndex);
    }

    @Override
    public int numberOfRows() {
        return owner().numberOfItems();
    }

    @Override
    public int numberOfColumns() {
        return owner().numberOfRows();
    }

    @Override
    public double at(int row, int col) {
        return owner().at(row, col, sliceIndex);
    }

}
