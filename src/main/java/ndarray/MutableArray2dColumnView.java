package ndarray;

import static java.util.Objects.requireNonNull;

/**
 * Date: 03.01.16
 * Time: 13:03
 *
 * @author Evgeny Antaev
 */
final class MutableArray2dColumnView extends Array2dColumnView implements MutableArray1d {
    private final MutableArray2d owner;

    MutableArray2dColumnView(MutableArray2d owner, int columnIndex) {
        super(columnIndex);
        this.owner = requireNonNull(owner);
    }

    @Override
    Array2d owner() {
        return owner;
    }

    @Override
    public MutableArray1d set(int i, double value) {
        owner.set(i, columnIndex, value);
        return this;
    }
}
