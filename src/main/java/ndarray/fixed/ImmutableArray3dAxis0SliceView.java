package ndarray.fixed;

import java.util.Objects;

/**
 * Date: 03.01.16
 * Time: 13:36
 *
 * @author Evgeny Antaev
 */
final class ImmutableArray3dAxis0SliceView extends Array3dAxis0SliceView implements ImmutableArray2d {
    private final ImmutableArray3d owner;

    ImmutableArray3dAxis0SliceView(ImmutableArray3d owner, int itemIndex) {
        super(itemIndex);
        this.owner = Objects.requireNonNull(owner);
    }

    @Override
    ImmutableArray3d owner() {
        return owner;
    }
}
