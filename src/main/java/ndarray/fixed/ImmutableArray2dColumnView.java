package ndarray.fixed;

/**
 * Date: 03.01.16
 * Time: 12:51
 *
 * @author Evgeny Antaev
 */
final class ImmutableArray2dColumnView extends Array2dColumnView implements ImmutableArray1d {
    private final ImmutableArray2d owner;

    ImmutableArray2dColumnView(ImmutableArray2d owner, int rowIndex) {
        super(rowIndex);
        this.owner = owner;
    }

    @Override
    Array2d owner() {
        return owner;
    }
}
