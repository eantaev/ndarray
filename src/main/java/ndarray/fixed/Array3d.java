package ndarray.fixed;

import javax.annotation.Nonnull;

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;

/**
 * Date: 02.01.16
 * Time: 18:21
 *
 * @author Evgeny Antaev
 */
public interface Array3d {

    int numberOfItems();

    int numberOfRows();

    int numberOfColumns();

    double at(int item, int row, int col);

    boolean isZero();

    Array2d sliceAxis0(int item);

    Array2d sliceAxis1(int row);

    Array2d sliceAxis2(int col);

    default ImmutableArray3d toImmutable() {
        return this instanceof ImmutableArray3d
            ? (ImmutableArray3d) this
            : FixedArrays.immutable3d.wrap(numberOfItems(), numberOfRows(), numberOfColumns(), readValues());

    }

    default MutableArray3d toMutable() {
        return this instanceof MutableArray3d
            ? (MutableArray3d) this
            : FixedArrays.mutable3d.wrap(numberOfItems(), numberOfRows(), numberOfColumns(), readValues());
    }

    default double[] readValues() {
        double[] values = new double[numberOfItems() * numberOfRows() * numberOfColumns()];
        readValues(values);
        return values;
    }

    default int readValues(@Nonnull double[] buffer) {
        requireNonNull(buffer, "buffer");
        int length = numberOfItems() * numberOfRows() * numberOfColumns();
        if (buffer.length < length) {
            throw new IllegalArgumentException(
                format("buffer size (%d) is not enough. Required %d", buffer.length, length));

        }
        int index = 0;
        for (int i = 0; i < numberOfItems(); ++i) {
            for (int r = 0; r < numberOfRows(); ++r) {
                for (int c = 0; c < numberOfColumns(); ++c) {
                    buffer[index++] = at(i, r, c);
                }
            }
        }
        return length;
    }
}
