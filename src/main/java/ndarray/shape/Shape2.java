package ndarray.shape;

import ndarray.Slider;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

import static ndarray.NdArrayUtils.checkNonNegative;
import static ndarray.pos.Positions.slider2d;

/**
 * Date: 19.01.16
 * Time: 0:57
 *
 * @author Evgeny Antaev
 */
final class Shape2 extends AbstractShape {
    private final int length;
    private final int width;

    Shape2(int length, int width) {
        checkNonNegative(length, "length < 0");
        checkNonNegative(width, "width < 0");
        this.length = length;
        this.width = width;
    }

    @Override
    public int numberOfAxes() {
        return 2;
    }

    @Override
    public int axisLength(@Nonnegative int axis) {
        switch (axis) {
            case 0:
                return length;
            case 1:
                return width;
        }
        throw new IllegalArgumentException("Axis " + axis + " is out of [0, " + numberOfAxes() + ')');
    }

    @Nonnull
    @Override
    public Slider slider() {
        return slider2d();
    }
}
