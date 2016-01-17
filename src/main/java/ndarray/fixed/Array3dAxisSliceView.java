package ndarray.fixed;

/**
 * Date: 03.01.16
 * Time: 13:32
 *
 * @author Evgeny Antaev
 */
abstract class Array3dAxisSliceView implements Array2d {
    final int sliceIndex;

    Array3dAxisSliceView(int sliceIndex) {
        this.sliceIndex = sliceIndex;
    }

    abstract Array3d owner();

    @Override
    public boolean isZero() {
        return owner().isZero();
    }
}
