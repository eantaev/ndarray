package ndarray.pos;

import ndarray.Slider;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

import static ndarray.NdArrayUtils.checkNonNegative;

/**
 * Date: 19.01.16
 * Time: 0:57
 *
 * @author Evgeny Antaev
 */
final class Slider1 extends Position1 implements Slider {

    Slider1(@Nonnegative int x) {
        super(x);
    }

    @Nonnull
    @Override
    public Slider move(@Nonnegative int axis, @Nonnegative int coordinate) {
        if (axis == 0) {
            return x(coordinate);
        }
        throw new IllegalArgumentException("Axis " + axis + " is out of [0, " + numberOfAxes() + ')');
    }

    @Nonnull
    @Override
    public Slider x(@Nonnegative int x) {
        checkNonNegative(x, "x < 0");
        this.x = x;
        return this;
    }

    @Nonnull
    @Override
    public Slider advanceX() {
        this.x++;
        return this;
    }
}
