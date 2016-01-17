package ndarray;

import javax.annotation.Nonnull;

/**
 * Date: 17.01.16
 * Time: 20:30
 *
 * @author Evgeny Antaev
 */
public final class Unpooled {
    private Unpooled() {
    }

    private static final ArrayFactory factory = new UnpooledArrayFactory();

    public static ArrayFactory factory() {
        return factory;
    }

    public static Array constantZero(@Nonnull Shape shape) {
        return factory.constantZero(shape);
    }

    public static Array create(@Nonnull Shape shape) {
        return factory.create(shape);
    }

    public static Array copyOf(@Nonnull Shape shape, @Nonnull double... data) {
        return factory.copyOf(shape, data);
    }

    public static Array copyOf(@Nonnull Array array) {
        return factory.copyOf(array);
    }

    public static Array wrap(@Nonnull Shape shape, @Nonnull double... data) {
        return factory.wrap(shape, data);
    }
}
