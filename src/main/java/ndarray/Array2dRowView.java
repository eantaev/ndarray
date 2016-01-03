package ndarray;

/**
 * Date: 03.01.16
 * Time: 12:47
 *
 * @author Evgeny Antaev
 */
abstract class Array2dRowView implements Array1d {
    final int rowIndex;

    Array2dRowView(int rowIndex) {
        this.rowIndex = rowIndex;
    }

    abstract Array2d owner();

    @Override
    public int length() {
        return owner().numberOfColumns();
    }

    @Override
    public double at(int index) {
        return owner().at(rowIndex, index);
    }
}
