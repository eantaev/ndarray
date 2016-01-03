package ndarray;

import java.util.Objects;

/**
 * Date: 03.01.16
 * Time: 13:40
 *
 * @author Evgeny Antaev
 */
final class MutableArray3dAxis0SliceView extends Array3dAxis0SliceView implements MutableArray2d {
    private final MutableArray3d owner;

    MutableArray3dAxis0SliceView(MutableArray3d owner, int itemIndex) {
        super(itemIndex);
        this.owner = Objects.requireNonNull(owner);
    }

    @Override
    MutableArray3d owner() {
        return owner;
    }

    @Override
    public MutableArray2d set(int row, int col, double value) {
        owner.set(itemIndex, row, col, value);
        return this;
    }
}
