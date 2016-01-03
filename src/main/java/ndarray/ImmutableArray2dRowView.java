package ndarray;

/**
 * Date: 03.01.16
 * Time: 12:47
 *
 * @author Evgeny Antaev
 */
final class ImmutableArray2dRowView extends Array2dRowView implements ImmutableArray1d {
    private final ImmutableArray2d owner;

    ImmutableArray2dRowView(ImmutableArray2d owner, int rowIndex) {
        super(rowIndex);
        this.owner = owner;
    }

    @Override
    ImmutableArray2d owner() {
        return owner;
    }
}
