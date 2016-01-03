package ndarray;

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
    public int hashCode() {
        return Arrays.hashCode(data);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (obj == this)
            return true;
        if (!(obj instanceof DenseArray1d))
            return false;

        DenseArray1d array = (DenseArray1d) obj;

        return Arrays.equals(data, array.data);
    }

    @Override
    public int readValues(double[] buffer) {
        requireNonNull(buffer, "buffer");
        System.arraycopy(data, 0, buffer, 0, length());
        return length();
    }
}
