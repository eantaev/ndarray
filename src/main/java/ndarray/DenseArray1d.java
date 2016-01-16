package ndarray;

import javax.annotation.Nullable;
import java.util.Arrays;

import static java.util.Objects.requireNonNull;

/**
 * Date: 29.12.15
 * Time: 0:40
 *
 * @author Evgeny Antaev
 */
class DenseArray1d extends AbstractArray1d {
    final double[] data;

    DenseArray1d(double[] data) {
        this.data = requireNonNull(data, "data");
    }

    public int length() {
        return data.length;
    }

    @Override
    public final double atUnchecked(int index) {
        return data[index];
    }

    @Override
    public boolean isZero() {
        return false;
    }

    @Override
    public String toString() {
        return Arrays.toString(data);
    }

    @Override
    public boolean equals(@Nullable Object o) {
        if (this == o) return true;
        if (o == null) return false;

        if (o instanceof DenseArray1d) {
            return Arrays.equals(data, ((DenseArray1d) o).data);
        } else {
            return super.equals(o);
        }
    }

    @Override
    public int readValues(double[] buffer) {
        requireNonNull(buffer, "buffer");
        System.arraycopy(data, 0, buffer, 0, length());
        return length();
    }
}
