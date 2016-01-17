package ndarray.fixed;

/**
 * Date: 03.01.16
 * Time: 13:03
 *
 * @author Evgeny Antaev
 */
final class MutableArray2dRowView extends Array2dRowView implements MutableArray1d {
    private final MutableArray2d owner;

    MutableArray2dRowView(MutableArray2d owner, int rowIndex) {
        super(rowIndex);
        this.owner = owner;
    }

    @Override
    MutableArray2d owner() {
        return owner;
    }

    @Override
    public MutableArray2dRowView set(int i, double value) {
        owner.set(sliceIndex, i, value);
        return this;
    }
}
