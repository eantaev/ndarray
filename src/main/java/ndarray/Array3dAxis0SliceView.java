package ndarray;

/**
 * Date: 03.01.16
 * Time: 13:32
 *
 * @author Evgeny Antaev
 */
abstract class Array3dAxis0SliceView implements Array2d {
    final int itemIndex;

    Array3dAxis0SliceView(int itemIndex) {
        this.itemIndex = itemIndex;
    }

    abstract Array3d owner();

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
        return owner().at(itemIndex, row, col);
    }
}
