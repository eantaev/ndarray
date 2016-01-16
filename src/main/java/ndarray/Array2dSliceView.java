package ndarray;

/**
 * Date: 03.01.16
 * Time: 12:47
 *
 * @author Evgeny Antaev
 */
abstract class Array2dSliceView extends AbstractArray1d {
    final int sliceIndex;

    Array2dSliceView(int sliceIndex) {
        this.sliceIndex = sliceIndex;
    }

    abstract Array2d owner();

    @Override
    public int length() {
        return owner().numberOfColumns();
    }

    @Override
    public boolean isZero() {
        return owner().isZero();
    }
}
