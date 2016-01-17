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
final class ArraySlice extends AbstractArray {
    private final Array owner;
    private final Shape shape;
    private final int sliceAxis;
    private final int sliceCoordinate;
    private final OwnerPosition ownerPosition;

    ArraySlice(@Nonnull Array owner, @Nonnegative int sliceAxis, @Nonnegative int sliceCoordinate) {
        this.owner = requireNonNull(owner, "owner");
        rangeCheck(sliceCoordinate, owner.shape().axisLength(sliceAxis));
        this.shape = owner.shape().slice(sliceAxis);
        this.sliceAxis = sliceAxis;
        this.sliceCoordinate = sliceCoordinate;
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
            rangeCheck(axis, owner.shape().numberOfAxes());
            if (axis < sliceAxis)
                return localPosition.coordinate(axis);
            if (axis > sliceAxis)
                return localPosition.coordinate(axis - 1);
            return sliceCoordinate;
        }
    }
}
