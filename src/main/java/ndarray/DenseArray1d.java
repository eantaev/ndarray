package ndarray;

import javax.annotation.Nullable;
import java.util.Arrays;

import static java.util.Objects.requireNonNull;
import static ndarray.NdArrayUtils.rangeCheck;

/**
 * Date: 29.12.15
 * Time: 0:40
 *
 * @author Evgeny Antaev
 */
public class DenseArray1d implements Array1d {
    final double[] data;

    DenseArray1d(double[] data) {
        this.data = requireNonNull(data, "data");
    }

    public int length() {
        return data.length;
    }

    @Override
    public final double at(int index) {
        rangeCheck(index, length());
        return data[index];
    }

    @Override
    public String toString() {
        return Arrays.toString(data);
    }

    @Override
    public boolean equals(@Nullable Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DenseArray1d that = (DenseArray1d) o;

        return Arrays.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(data);
    }

    @Override
    public int readValues(double[] buffer) {
        requireNonNull(buffer, "buffer");
        System.arraycopy(data, 0, buffer, 0, length());
        return length();
    }
}
