package ndarray.fixed;

/**
 * Date: 03.01.16
 * Time: 12:48
 *
 * @author Evgeny Antaev
 */
abstract class Array2dColumnView extends Array2dSliceView {

    Array2dColumnView(int colIndex) {
        super(colIndex);
    }

    @Override
    public int length() {
        return owner().numberOfRows();
    }

    @Override
    public double atUnchecked(int index) {
        return owner().at(index, sliceIndex);
    }

}
