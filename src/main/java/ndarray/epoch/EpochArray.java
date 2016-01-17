package ndarray.epoch;

import ndarray.*;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;

import static java.util.Objects.requireNonNull;
import static ndarray.NdArrayUtils.throwIllegalArgument;
import static ndarray.NdArrayUtils.throwIllegalState;

/**
 * Date: 17.01.16
 * Time: 18:52
 *
 * @author Evgeny Antaev
 */
@NotThreadSafe
final class EpochArray extends AbstractArray {

    @Nonnull
    private final Shape shape;
    @Nonnull
    private final EpochArrayAllocator alloc;
    private final int epoch;
    private final int offset;

    EpochArray(@Nonnull Shape shape, @Nonnull EpochArrayAllocator alloc, @Nonnegative int offset) {
        this.shape = requireNonNull(shape, "shape");
        this.alloc = requireNonNull(alloc, "alloc");
        this.epoch = alloc.epoch;
        this.offset = offset;
    }

    private int dataIndex(@Nonnull Position position) {
        return offset + shape.elementIndex(position);
    }

    private void checkEpoch() {
        if (this.epoch != alloc.epoch)
            throw new EpochChangedException();
    }

    @Nonnull
    @Override
    public Shape shape() {
        checkEpoch();
        return shape;
    }

    @Override
    public final double at(@Nonnull Position position) {
        checkEpoch();
        if (isConstantZero()) {
            shape.checkPosition(position);
            return 0;
        }
        return alloc.get(dataIndex(position));
    }

    @Override
    public void set(@Nonnull Position position, double value) {
        checkEpoch();
        if (isConstantZero()) {
            throwIllegalState("Array is constant zero and cannot be modified");
        }
        alloc.set(dataIndex(position), value);
    }

    @Override
    public boolean isConstantZero() {
        checkEpoch();
        return false;
    }

    @Override
    public void forEach(@Nonnull ArrayConsumer consumer) {
        checkEpoch();
        super.forEach(consumer);
    }

    @Nonnull
    @Override
    public Array slice(@Nonnegative int axis, @Nonnegative int coordinate) {
        checkEpoch();
        return super.slice(axis, coordinate);
    }

    @Override
    public String toString() {
        checkEpoch();
        return super.toString();
    }

    @Override
    public boolean equals(@Nullable Object o) {
        checkEpoch();
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        checkEpoch();
        return super.hashCode();
    }

    @Nonnull
    @Override
    public double[] readValues() {
        checkEpoch();
        double[] buffer = new double[shape.numberOfElements()];
        readValues(buffer);
        return buffer;
    }

    @Override
    public int readValues(@Nonnull double[] buffer, int bufferOffset) {
        checkEpoch();
        requireNonNull(buffer, "buffer");
        int numberOfElements = shape.numberOfElements();
        if (buffer.length < bufferOffset + numberOfElements) {
            throwIllegalArgument(
                "buffer size (%d) is not enough. Required %d + %d", buffer.length, bufferOffset, numberOfElements);
        }
        alloc.readBlock(offset, numberOfElements, buffer, bufferOffset);
        return numberOfElements;
    }
}