package ndarray;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;

/**
 * Date: 16.01.16
 * Time: 13:06
 *
 * @author Evgeny Antaev
 */
public abstract class AbstractArray implements Array {

    @Nonnull
    @Override
    public Slider slider() {
        return shape().slider();
    }

    @Override
    public void forEach(@Nonnull ArrayConsumer consumer) {
        for (Position pos : shape()) {
            consumer.accept(pos, at(pos));
        }
    }

    @Nonnull
    @Override
    public Array slice(@Nonnegative int axis, @Nonnegative int coordinate) {
        return new ArraySlice(this, axis, coordinate);
    }

    @Nonnull
    public double[] readValues() {
        double[] values = new double[shape().numberOfElements()];
        readValues(values);
        return values;
    }

    @Override
    public int readValues(@Nonnull double[] buffer) {
        return readValues(buffer, 0);
    }

    @Override
    public int readValues(@Nonnull double[] buffer, int offset) {
        requireNonNull(buffer, "buffer");
        int numberOfElements = shape().numberOfElements();
        if (buffer.length < numberOfElements) {
            throw new IllegalArgumentException(
                format("buffer size (%d) is not enough. Required %d", buffer.length, numberOfElements));
        }
        int i = offset;
        for (Position pos : shape()) {
            buffer[i++] = at(pos);
        }
        return numberOfElements;
    }

    @Override
    public String toString() {
        int size = shape().numberOfElements();
        if (size == 0)
            return "[/]";
        StringBuilder sb = new StringBuilder(size * 8);
        sb.append('[');
        forEach((pos, val) -> sb.append(pos).append("->").append(val).append(' '));
        sb.append(']');
        return sb.toString();
    }

    @Override
    public boolean equals(@Nullable Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof Array)) return false;

        Array that = (Array) o;

        return shape().equals(that.shape())
            && shape().positionStream().noneMatch(pos -> this.at(pos) != that.at(pos));
    }

    @Override
    public int hashCode() {
        int result = 1;
        for (Position pos : shape()) {
            long bits = Double.doubleToLongBits(at(pos));
            result = 31 * result + (int) (bits ^ (bits >>> 32));
        }
        return 31 * shape().hashCode();
    }
}
