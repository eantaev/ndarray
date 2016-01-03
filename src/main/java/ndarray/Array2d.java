package ndarray;

import javax.annotation.Nonnull;

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;

/**
 * Date: 29.12.15
 * Time: 0:29
 *
 * @author Evgeny Antaev
 */
public interface Array2d {
    int numberOfRows();

    int numberOfColumns();

    double at(int row, int col);

    Array1d sliceAxis0(int row);

    Array1d sliceAxis1(int col);

    default ImmutableArray2d toImmutable() {
        return this instanceof ImmutableArray2d
            ? (ImmutableArray2d) this
            : NdArrays.immutable2d.wrap(numberOfRows(), numberOfColumns(), readValues());
    }

    default MutableArray2d toMutable() {
        return this instanceof MutableArray2d
            ? (MutableArray2d) this
            : NdArrays.mutable2d.wrap(numberOfRows(), numberOfColumns(), readValues());
    }

    default double[] readValues() {
        double[] values = new double[numberOfRows() * numberOfColumns()];
        readValues(values);
        return values;
    }

    default int readValues(@Nonnull double[] buffer) {
        requireNonNull(buffer, "buffer");
        int length = numberOfRows() * numberOfColumns();
        if (buffer.length < length) {
            throw new IllegalArgumentException(
                format("buffer size (%d) is not enough. Required %d", buffer.length, length));

        }
        int index = 0;
        for (int r = 0; r < numberOfRows(); ++r) {
            for (int c = 0; c < numberOfColumns(); ++c) {
                buffer[index++] = at(r, c);
            }
        }
        return length;
    }
}
