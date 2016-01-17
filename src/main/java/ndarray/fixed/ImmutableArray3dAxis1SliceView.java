package ndarray.fixed;

import java.util.Objects;

/**
 * Date: 03.01.16
 * Time: 13:38
 *
 * @author Evgeny Antaev
 */
final class ImmutableArray3dAxis1SliceView extends Array3dAxis1SliceView implements ImmutableArray2d{
    private final ImmutableArray3d owner;

    ImmutableArray3dAxis1SliceView(ImmutableArray3d owner, int rowIndex) {
        super(rowIndex);
        this.owner = Objects.requireNonNull(owner);
    }

    @Override
    ImmutableArray3d owner() {
        return owner;
    }
}
