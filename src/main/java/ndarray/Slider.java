package ndarray;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

/**
 * Date: 19.01.16
 * Time: 1:44
 *
 * @author Evgeny Antaev
 */
public interface Slider extends Position {
    @Nonnull
    Slider move(@Nonnegative int axis, @Nonnegative int coordinate);

    @Nonnull
    default Slider x(@Nonnegative int coordinate) {
        move(0, coordinate);
        return this;
    }

    @Nonnull
    default Slider y(@Nonnegative int coordinate) {
        move(1, coordinate);
        return this;
    }

    @Nonnull
    default Slider z(@Nonnegative int coordinate) {
        move(2, coordinate);
        return this;
    }

    @Nonnull
    default Slider advance(@Nonnegative int axis) {
        return move(axis, coordinate(axis) + 1);
    }

    @Nonnull
    default Slider advanceX() {
        return advance(0);
    }

    @Nonnull
    default Slider advanceY() {
        return advance(1);
    }

    @Nonnull
    default Slider advanceZ() {
        return advance(2);
    }
}
