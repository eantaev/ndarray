package ndarray.alloc.epoch;

import ndarray.*;
import ndarray.alloc.ArrayAllocator;

import java.util.Arrays;

import static java.util.Objects.requireNonNull;

/**
 * Date: 11.01.16
 * Time: 23:48
 *
 * @author Evgeny Antaev
 */
public final class EpochArrayAllocator implements ArrayAllocator {
    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;

    private final Array1dFactory<ImmutableArray1d> immutable1d;
    private final Array1dFactory<MutableArray1d> mutable1d;

    private double[] data;
    private int freePointer;

    int epoch;

    public static EpochArrayAllocator withInitialSize(int initialSize) {
        return new EpochArrayAllocator(initialSize);
    }

    private EpochArrayAllocator(int initialSize) {
        this.data = new double[initialSize];
        this.epoch = 0;
        this.freePointer = 0;
        this.immutable1d = new EpochImmutableArray1dFactory(this);
        this.mutable1d = new EpochMutableArray1dFactory(this);
    }

    @Override
    public void newEpoch() {
        ++epoch;
        freePointer = 0;
    }

    @Override
    public Array1dFactory<ImmutableArray1d> immutable1d() {
        return immutable1d;
    }

    @Override
    public Array1dFactory<MutableArray1d> mutable1d() {
        return mutable1d;
    }

    double get(int index) {
        return data[index];
    }

    void set(int index, double value) {
        data[index] = value;
    }

    int allocateBlock(int length) {
        if (length < 0) {
            throw new IllegalArgumentException("length < 0");
        }
        ensureCapacity(freePointer + length);
        int blockPointer = freePointer;
        freePointer += length;
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

    void readBlock(int offset, int length, double[] buffer) {
        requireNonNull(buffer, "buffer");
        System.arraycopy(data, offset, buffer, 0, length);
    }

    void writeBlock(int offset, int length, double[] buffer) {
        requireNonNull(buffer, "buffer");
        if (buffer.length < length) {
            throw new IllegalArgumentException("buffer is smaller than array");
        }
        System.arraycopy(buffer, 0, data, offset, length);
    }

    void fill(int offset, int length, double value) {
        Arrays.fill(data, offset, offset + length, value);
    }

}
