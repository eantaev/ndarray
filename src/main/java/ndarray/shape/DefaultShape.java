package ndarray.shape;

import javax.annotation.Nonnegative;
import java.util.Arrays;

import static java.util.Objects.requireNonNull;
import static ndarray.NdArrayUtils.*;

/**
 * Date: 19.01.16
 * Time: 1:15
 *
 * @author Evgeny Antaev
 */
final class DefaultShape extends AbstractShape {

    private final int[] dimensions;

    DefaultShape(int[] dimensions) {
        requireNonNull(dimensions, "dimensions");
        if (dimensions.length == 0) {
            throwIllegalArgument("Dimensions shouldn't be empty");
        }
        if (!allNonNegative(dimensions)) {
            throwIllegalArgument("Dimensions should be >= 0. Got: " + Arrays.toString(dimensions));
        }
        this.dimensions = dimensions;
    }

    @Override
    public int numberOfAxes() {
        return dimensions.length;
    }

    @Override
    public int axisLength(@Nonnegative int axis) {
        rangeCheck(axis, numberOfAxes());
        return dimensions[axis];
    }

    @Override
    public int numberOfElements() {
        int n = 1;
        for (int dimension : dimensions) {
            n *= dimension;
        }
        return n;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;

        return o instanceof DefaultShape
            ? Arrays.equals(dimensions, ((DefaultShape) o).dimensions)
            : super.equals(o);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(dimensions);
    }

    @Override
    public String toString() {
        return "Shape" + Arrays.toString(dimensions);
    }
}
