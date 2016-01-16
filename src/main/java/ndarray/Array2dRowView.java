package ndarray;

/**
 * Date: 03.01.16
 * Time: 12:47
 *
 * @author Evgeny Antaev
 */
abstract class Array2dRowView extends Array2dSliceView {

    Array2dRowView(int rowIndex) {
        super(rowIndex);
    }

    @Override
    public int length() {
        return owner().numberOfColumns();
    }

    @Override
    public double atUnchecked(int index) {
        return owner().at(sliceIndex, index);
    }

}
