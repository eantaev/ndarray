package ndarray;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.NotThreadSafe;
import java.util.Arrays;

import static java.util.Objects.requireNonNull;
import static ndarray.NdArrayUtils.throwIllegalArgument;
import static ndarray.NdArrayUtils.throwIllegalState;

/**
 * Date: 29.12.15
 * Time: 0:36
 *
 * @author Evgeny Antaev
 */
@NotThreadSafe
final class DenseArray extends AbstractArray {
    private static final double[] ZERO_DATA = new double[0];

    @Nonnull
    final Shape shape;
    @Nonnull
    final double[] data;

    public static Array immutableZero(@Nonnull Shape shape) {
        return new DenseArray(shape, ZERO_DATA);
    }

    public static Array create(@Nonnull Shape shape) {
        requireNonNull(shape, "shape");
        return wrap(shape, new double[shape.numberOfElements()]);
    }

    public static Array wrap(@Nonnull Shape shape, @Nonnull double[] data) {
        requireNonNull(shape, "shape");
        requireNonNull(data, "data");
        int numberOfElements = shape.numberOfElements();
        if (numberOfElements != data.length) {
            throwIllegalArgument("(data.length = %d) != (shape's numberOfElements = %d)",
                data.length, numberOfElements);
        }
        return new DenseArray(shape, data);
    }

    public static Array copyOf(@Nonnull Shape shape, @Nonnull double[] data) {
        requireNonNull(data, "data");
        return wrap(shape, Arrays.copyOf(data, data.length));
    }

    private DenseArray(@Nonnull Shape shape, @Nonnull double[] data) {
        this.shape = requireNonNull(shape, "shape");
        this.data = requireNonNull(data, "data");
    }

    @Nonnull
    @Override
    public Shape shape() {
        return shape;
    }

    @Override
    public final double at(@Nonnull Position position) {
        if (isConstantZero()) {
            shape.checkPosition(position);
            return 0;
        }
        return data[shape.elementIndex(position)];
    }

    @Override
    public void set(@Nonnull Position position, double value) {
        if (isConstantZero()) {
            throwIllegalState("Array is constant zero and cannot be modified");
        }
        data[shape.elementIndex(position)] = value;
    }

    @Override
    public boolean isConstantZero() {
        return data == ZERO_DATA;
    }

    @Override
    public int hashCode() {
        return 31 * Arrays.hashCode(data) + shape.hashCode();
    }

    @Nonnull
    @Override
    public double[] readValues() {
        if (isConstantZero()) {
            return ZERO_DATA;
        }
        return Arrays.copyOf(data, data.length);
    }

    @Override
    public int readValues(@Nonnull double[] buffer, int bufferOffset) {
        requireNonNull(buffer, "buffer");
        if (bufferOffset < 0) {
            throwIllegalArgument("offset < 0");
        }
        if (isConstantZero()) {
            return 0;
        }
        int numberOfElements = data.length;
        if (buffer.length < bufferOffset + numberOfElements) {
            throwIllegalArgument(
                "buffer size (%d) is not enough. Required %d + %d", buffer.length, bufferOffset, numberOfElements);
        }
        System.arraycopy(data, 0, buffer, bufferOffset, numberOfElements);
        return numberOfElements;
    }
}
