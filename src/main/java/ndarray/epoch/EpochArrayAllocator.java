package ndarray.epoch;

import ndarray.Unpooled;
import ndarray.Array;
import ndarray.ArrayFactory;
import ndarray.Shape;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.NotThreadSafe;
import java.util.Arrays;

import static java.util.Objects.requireNonNull;
import static ndarray.NdArrayUtils.throwIllegalArgument;

/**
 * Date: 11.01.16
 * Time: 23:48
 *
 * @author Evgeny Antaev
 */
@NotThreadSafe
public final class EpochArrayAllocator implements ArrayFactory {
    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;

    private double[] data;
    private int freePointer;

    int epoch;

    public static EpochArrayAllocator withInitialSize(int initialSize) {
        return new EpochArrayAllocator(initialSize);
    }

    private EpochArrayAllocator(int initialSize) {
        if (initialSize < 0) {
            throwIllegalArgument("initialSize < 0");
        }
        this.data = new double[initialSize];
        this.epoch = 0;
        this.freePointer = 0;
    }

    public int allocated() {
        return freePointer;
    }

    public int bufferSize() {
        return data.length;
    }

    public int available() {
        return bufferSize() - allocated();
    }

    public void newEpoch() {
        ++epoch;
        freePointer = 0;
    }

    public void destroy() {
        newEpoch();
        this.data = null;
    }

    double get(int index) {
        return data[index];
    }

    void set(int index, double value) {
        data[index] = value;
    }

    int allocateBlock(int length, boolean initialize) {
        if (length < 0) {
            throw new IllegalArgumentException("length < 0");
        }
        ensureCapacity(freePointer + length);
        int blockPointer = freePointer;
        freePointer += length;

        if (initialize) {
            Arrays.fill(data, blockPointer, blockPointer + length, 0);
        }
        return blockPointer;
    }

    private void ensureCapacity(int minCapacity) {
        // overflow-conscious code
        if (minCapacity - data.length > 0)
            grow(minCapacity);
    }

    private void grow(int minCapacity) {
        // overflow-conscious code
        int oldCapacity = data.length;
        int newCapacity = oldCapacity + (oldCapacity >> 1);
        if (newCapacity - minCapacity < 0)
            newCapacity = minCapacity;
        if (newCapacity - MAX_ARRAY_SIZE > 0)
            newCapacity = hugeCapacity(minCapacity);
        // minCapacity is usually close to size, so this is a win:
        data = Arrays.copyOf(data, newCapacity);
    }

    private static int hugeCapacity(int minCapacity) {
        if (minCapacity < 0) // overflow
            throw new OutOfMemoryError();
        return minCapacity > MAX_ARRAY_SIZE ? Integer.MAX_VALUE : MAX_ARRAY_SIZE;
    }

    void readBlock(int offset, int length, double[] dest, int destOffset) {
        requireNonNull(dest, "dest");
        System.arraycopy(data, offset, dest, destOffset, length);
    }

    void writeBlock(int offset, int length, double[] buffer) {
        requireNonNull(buffer, "buffer");
        if (buffer.length < length) {
            throw new IllegalArgumentException("buffer is smaller than array");
        }
        System.arraycopy(buffer, 0, data, offset, length);
    }

    @Override
    public Array constantZero(@Nonnull Shape shape) {
        return Unpooled.constantZero(shape);
    }

    @Override
    public Array create(@Nonnull Shape shape) {
        requireNonNull(shape, "shape");
        return new EpochArray(shape, this, allocateBlock(shape.numberOfElements(), true));
    }

    public Array createNoInitialize(@Nonnull Shape shape) {
        requireNonNull(shape, "shape");
        return new EpochArray(shape, this, allocateBlock(shape.numberOfElements(), false));
    }

    @Override
    public Array copyOf(@Nonnull Shape shape, @Nonnull double... values) {
        requireNonNull(shape, "shape");
        requireNonNull(values, "values");
        int blockLength = shape.numberOfElements();
        if (blockLength != values.length) {
            throwIllegalArgument("(values.length = %d) != (shape's numberOfElements = %d)",
                values.length, blockLength);
        }
        int blockPointer = allocateBlock(blockLength, false);
        writeBlock(blockPointer, blockLength, values);
        return new EpochArray(shape, this, blockPointer);
    }

    @Override
    public Array copyOf(@Nonnull Array array) {
        requireNonNull(array, "array");
        Shape shape = array.shape();
        int blockLength = shape.numberOfElements();
        int blockPointer = allocateBlock(blockLength, false);
        array.readValues(data, blockPointer);
        return new EpochArray(shape, this, blockPointer);
    }

    @Override
    public Array wrap(@Nonnull Shape shape, @Nonnull double... data) {
        return Unpooled.wrap(shape, data);
    }
}
