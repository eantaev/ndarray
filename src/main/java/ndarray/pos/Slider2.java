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
final class Slider2 extends Position2 implements Slider {

    Slider2(@Nonnegative int x, @Nonnegative int y) {
        super(x, y);
    }

    @Nonnull
    @Override
    public Slider move(@Nonnegative int axis, @Nonnegative int coordinate) {
        switch (axis) {
            case 0:
                return x(coordinate);
            case 1:
                return y(coordinate);
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
    public Slider y(@Nonnegative int y) {
        checkNonNegative(y, "y < 0");
        this.y = y;
        return this;
    }

    @Nonnull
    @Override
    public Slider advanceX() {
        this.x++;
        return this;
    }

    @Nonnull
    @Override
    public Slider advanceY() {
        this.y++;
        return this;
    }
}
