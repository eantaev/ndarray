package ndarray;

import java.util.Objects;

/**
 * Date: 03.01.16
 * Time: 13:38
 *
 * @author Evgeny Antaev
 */
final class ImmutableArray3dAxis2SliceView extends Array3dAxis2SliceView implements ImmutableArray2d {
    private final ImmutableArray3d owner;

    ImmutableArray3dAxis2SliceView(ImmutableArray3d owner, int colIndex) {
        super(colIndex);
        this.owner = Objects.requireNonNull(owner);
    }

    @Override
    ImmutableArray3d owner() {
        return owner;
    }
}
