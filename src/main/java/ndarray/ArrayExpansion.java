package ndarray;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.concurrent.NotThreadSafe;

import static java.util.Objects.requireNonNull;
import static ndarray.NdArrayUtils.rangeCheck;

/**
 * Date: 17.01.16
 * Time: 16:50
 *
 * @author Evgeny Antaev
 */
@NotThreadSafe
final class ArrayExpansion extends AbstractArray {
    private final Array owner;
    private final Shape shape;
    private final int insertedAxis;
    private final OwnerPosition ownerPosition;

    ArrayExpansion(@Nonnull Array owner, @Nonnegative int insertedAxis, @Nonnegative int axisLength) {
        this.owner = requireNonNull(owner, "owner");
        this.shape = owner.shape().insert(insertedAxis, axisLength);
        this.insertedAxis = insertedAxis;
        this.ownerPosition = new OwnerPosition();
    }

    @Nonnull
    @Override
    public Shape shape() {
        return shape;
    }

    @Override
    public double at(@Nonnull Position p) {
        return owner.at(ownerPosition.at(p));
    }

    @Override
    public void set(@Nonnull Position position, double value) {
        owner.set(ownerPosition.at(position), value);
    }

    @Override
    public boolean isConstantZero() {
        return owner.isConstantZero();
    }

    private final class OwnerPosition implements Position {
        private Position localPosition;

        private OwnerPosition at(@Nonnull Position delegate) {
            this.localPosition = requireNonNull(delegate);
            return this;
        }

        @Override
        public int numberOfAxes() {
            return owner.shape().numberOfAxes();
        }

        @Override
        public int coordinate(int axis) {
            rangeCheck(axis, numberOfAxes());
            return localPosition.coordinate(axis < insertedAxis ? axis : axis + 1);
        }
    }
}
