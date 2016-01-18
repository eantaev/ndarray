package ndarray.shape;

import ndarray.Shape;

/**
 * Date: 16.01.16
 * Time: 14:04
 *
 * @author Evgeny Antaev
 */
public final class Shapes {
    private Shapes() {
    }

    public static Shape shape(int length) {
        return new Shape1(length);
    }

    public static Shape shape(int length, int width) {
        return new Shape2(length, width);
    }

    public static Shape shape(int length, int width, int height) {
        return new Shape3(length, width, height);
    }

    public static Shape shape(int... dimensions) {
        return new DefaultShape(dimensions);
    }
}

