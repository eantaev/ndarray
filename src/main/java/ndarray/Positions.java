package ndarray;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static java.util.Objects.requireNonNull;
import static ndarray.NdArrayUtils.throwIllegalArgument;
import static ndarray.NdArrayUtils.allNonNegative;
import static ndarray.NdArrayUtils.rangeCheck;

/**
 * Date: 16.01.16
 * Time: 14:28
 *
 * @author Evgeny Antaev
 */
public final class Positions {
    private Positions() {
    }

    public static Position position(int... coordinates) {
        return DefaultPosition.wrap(coordinates);
    }

    private static final class DefaultPosition implements Position {
        private final int[] coordinates;

        private static DefaultPosition wrap(int[] coordinates) {
            requireNonNull(coordinates, "coordinates");
            if (coordinates.length == 0) {
                throwIllegalArgument("Coordinates shouldn't be empty");
            }
            if (!allNonNegative(coordinates)) {
                throwIllegalArgument("Coordinates should be >= 0. Got: " + Arrays.toString(coordinates));
            }
            return new DefaultPosition(coordinates);
        }

        private DefaultPosition(int[] coordinates) {
            this.coordinates = coordinates;
        }

        @Override
        public int numberOfAxes() {
            return coordinates.length;
        }

        @Override
        public int coordinate(int axis) {
            rangeCheck(axis, numberOfAxes());
            return coordinates[axis];
        }

        private void setCoordinate(int axis, int coordinate) {
            coordinates[axis] = coordinate;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            DefaultPosition that = (DefaultPosition) o;

            return Arrays.equals(coordinates, that.coordinates);

        }

        @Override
        public int hashCode() {
            return Arrays.hashCode(coordinates);
        }

        @Override
        public String toString() {
            return "Pos" + Arrays.toString(coordinates);
        }
    }

    static class PositionIterator implements Iterator<Position> {
        @Nonnull
        private final Shape shape;
        @Nonnull
        private final DefaultPosition position;

        PositionIterator(@Nonnull Shape shape) {
            this.shape = requireNonNull(shape, "shape");

            int[] coordinates = new int[shape.numberOfAxes()];
            coordinates[shape.numberOfAxes() - 1] = -1;

            this.position = new DefaultPosition(coordinates);
        }

        @Override
        public boolean hasNext() {
            for (int axis = 0; axis < shape.numberOfAxes(); ++axis) {
                if (position.coordinate(axis) + 1 != shape.axisLength(axis)) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public Position next() {
            for (int axis = shape.numberOfAxes() - 1; axis >= 0; --axis) {
                if (position.coordinate(axis) + 1 == shape.axisLength(axis)) {
                    position.setCoordinate(axis, 0);
                } else {
                    position.setCoordinate(axis, position.coordinate(axis) + 1);
                    return position;
                }
            }
            throw new NoSuchElementException("no more positions");
        }
    }
}
