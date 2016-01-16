package ndarray.alloc.epoch;

import ndarray.AbstractArray1d;

import static java.util.Objects.requireNonNull;

/**
 * Date: 14.01.16
 * Time: 0:35
 *
 * @author Evgeny Antaev
 */
abstract class EpochArray1d extends AbstractArray1d {
    final EpochArrayAllocator alloc;
    final int epoch;
    final int offset;
    final int length;

    EpochArray1d(EpochArrayAllocator alloc, int offset, int length) {
        this.alloc = requireNonNull(alloc, "alloc");
        this.epoch = alloc.epoch;
        this.offset = offset;
        this.length = length;
    }

    public int length() {
        return length;
    }

    int dataIndex(int index) {
        return offset + index;
    }

    void checkEpoch() {
        if (this.epoch != alloc.epoch)
            throw new IllegalStateException("Epoch has changed. This object is no longer valid.");
    }

    @Override
    public final double atUnchecked(int index) {
        checkEpoch();
        return alloc.get(dataIndex(index));
    }

    @Override
    public boolean isZero() {
        checkEpoch();
        return false;
    }

    @Override
    public int readValues(double[] buffer) {
        checkEpoch();
        requireNonNull(buffer, "buffer");
        if (buffer.length != length) {
            throw new IllegalArgumentException("buffer size <> array size");
        }
        alloc.readBlock(offset, length, buffer);
        return length;
    }

    void fill(double value) {
        checkEpoch();
        alloc.fill(offset, length, value);
    }

    int writeValues(double[] buffer) {
        checkEpoch();
        requireNonNull(buffer, "buffer");
        if (buffer.length < length) {
            throw new IllegalArgumentException("buffer is smaller than array");
        }
        alloc.writeBlock(offset, length, buffer);
        return length;
    }
}
