package ndarray.pos;

import ndarray.Position;
import ndarray.Shape;
import ndarray.Slider;

import javax.annotation.Nonnull;

/**
 * Date: 16.01.16
 * Time: 14:28
 *
 * @author Evgeny Antaev
 */
public final class Positions {
    private Positions() {
    }

    @Nonnull
    public static Position position(int coordinate) {
        return new Position1(coordinate);
    }

    @Nonnull
    public static Position position(int x, int y) {
        return new Position2(x, y);
    }

    @Nonnull
    public static Position position(int x, int y, int z) {
        return new Position3(x, y, z);
    }

    @Nonnull
    public static Position position(int... coordinates) {
        return DefaultPosition.wrap(coordinates);
    }

    @Nonnull
    public static Slider slider(@Nonnull Shape shape) {
        return DefaultPosition.wrap(new int[shape.numberOfAxes()]);
    }

    @Nonnull
    public static Slider slider1d() {
        return slider(0);
    }

    @Nonnull
    public static Slider slider2d() {
        return slider(0, 0);
    }

    @Nonnull
    public static Slider slider3d() {
        return slider(0, 0, 0);
    }

    @Nonnull
    public static Slider slider(int x) {
        return new Slider1(x);
    }

    @Nonnull
    public static Slider slider(int x, int y) {
        return new Slider2(x, y);
    }

    @Nonnull
    public static Slider slider(int x, int y, int z) {
        return new Slider3(x, y, z);
    }
}
