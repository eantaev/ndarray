package ndarray;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.ThreadSafe;

import static java.util.Objects.requireNonNull;

/**
 * Date: 17.01.16
 * Time: 17:04
 *
 * @author Evgeny Antaev
 */
@ThreadSafe
final class UnpooledArrayFactory implements ArrayFactory {
    @Override
    public Array constantZero(@Nonnull Shape shape) {
        return DenseArray.immutableZero(shape);
    }

    @Override
    public Array create(@Nonnull Shape shape) {
        return DenseArray.create(shape);
    }

    @Override
    public Array copyOf(@Nonnull Shape shape, @Nonnull double... data) {
        return DenseArray.copyOf(shape, data);
    }

    @Override
    public Array copyOf(@Nonnull Array array) {
        requireNonNull(array, "array");
        return wrap(array.shape(), array.readValues());
    }

    @Override
    public Array wrap(@Nonnull Shape shape, @Nonnull double... data) {
        return DenseArray.wrap(shape, data);
    }
}
