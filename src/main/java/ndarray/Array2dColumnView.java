package ndarray;

/**
 * Date: 03.01.16
 * Time: 12:48
 *
 * @author Evgeny Antaev
 */
abstract class Array2dColumnView implements Array1d {
    final int columnIndex;

    Array2dColumnView(int columnIndex) {
        this.columnIndex = columnIndex;
    }

    abstract Array2d owner();

    @Override
    public int length() {
        return owner().numberOfRows();
    }

    @Override
    public double at(int index) {
        return owner().at(index, columnIndex);
    }
}
