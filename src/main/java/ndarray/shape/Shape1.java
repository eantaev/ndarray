package ndarray.shape;

import ndarray.Slider;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

import static ndarray.NdArrayUtils.checkNonNegative;
import static ndarray.pos.Positions.slider1d;

/**
 * Date: 19.01.16
 * Time: 0:57
 *
 * @author Evgeny Antaev
 */
final class Shape1 extends AbstractShape {
    private final int length;

    Shape1(int length) {
        checkNonNegative(length, "length < 0");
        this.length = length;
    }

    @Override
    public int numberOfAxes() {
        return 1;
    }

    @Override
    public int axisLength(@Nonnegative int axis) {
        if (axis == 0)
            return length;
        throw new IllegalArgumentException("Axis " + axis + " is out of [0, " + numberOfAxes() + ')');
    }

    @Nonnull
    @Override
    public Slider slider() {
        return slider1d();
    }
}
