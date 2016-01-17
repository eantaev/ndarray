package ndarray.fixed;

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;

/**
 * Date: 29.12.15
 * Time: 0:28
 *
 * @author Evgeny Antaev
 */
public interface Array1d {
    int length();

    double at(int index);

    boolean isZero();

    default ImmutableArray1d toImmutable() {
        return this instanceof ImmutableArray1d
            ? (ImmutableArray1d) this
            : FixedArrays.immutable1d.wrap(readValues());
    }

    default MutableArray1d toMutable() {
        return this instanceof MutableArray1d
            ? (MutableArray1d) this
            : FixedArrays.mutable1d.wrap(readValues());
    }

    default double[] readValues() {
        double[] values = new double[length()];
        readValues(values);
        return values;
    }

    default int readValues(double[] buffer) {
        requireNonNull(buffer, "buffer");
        if (buffer.length < length()) {
            throw new IllegalArgumentException(
                format("buffer size (%d) is not enough. Required %d", buffer.length, length()));
        }
        for (int i = 0; i < length(); ++i) {
            buffer[i] = at(i);
        }
        return length();
    }

}
