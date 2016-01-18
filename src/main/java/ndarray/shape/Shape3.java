package ndarray.shape;

import ndarray.Slider;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

import static ndarray.NdArrayUtils.checkNonNegative;
import static ndarray.pos.Positions.slider3d;

/**
 * Date: 19.01.16
 * Time: 0:57
 *
 * @author Evgeny Antaev
 */
final class Shape3 extends AbstractShape {
    private final int length;
    private final int width;
    private final int height;

    Shape3(int length, int width, int height) {
        checkNonNegative(length, "length < 0");
        checkNonNegative(width, "width < 0");
        checkNonNegative(height, "height < 0");
        this.length = length;
        this.width = width;
        this.height = height;
    }

    @Override
    public int numberOfAxes() {
        return 3;
    }

    @Override
    public int axisLength(@Nonnegative int axis) {
        switch (axis) {
            case 0:
                return length;
            case 1:
                return width;
            case 2:
                return height;
        }
        throw new IllegalArgumentException("Axis " + axis + " is out of [0, " + numberOfAxes() + ')');
    }

    @Nonnull
    @Override
    public Slider slider() {
        return slider3d();
    }
}
