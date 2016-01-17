package ndarray;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static java.util.Objects.requireNonNull;
import static java.util.Spliterator.*;
import static ndarray.NdArrayUtils.throwIllegalArgument;
import static ndarray.NdArrayUtils.throwIllegalState;
import static ndarray.NdArrayUtils.allNonNegative;
import static ndarray.NdArrayUtils.rangeCheck;

/**
 * Date: 16.01.16
 * Time: 14:04
 *
 * @author Evgeny Antaev
 */
public final class Shapes {
    private Shapes() {
    }

    public static Shape shape(int... dimensions) {
        return new DefaultShape(dimensions);
    }

    private static class DefaultShape implements Shape {

        private final int[] dimensions;

        private DefaultShape(int[] dimensions) {
            requireNonNull(dimensions, "dimensions");
            if (dimensions.length == 0) {
                throwIllegalArgument("Dimensions shouldn't be empty");
            }
            if (!allNonNegative(dimensions)) {
                throwIllegalArgument("Dimensions should be >= 0. Got: " + Arrays.toString(dimensions));
            }
            this.dimensions = dimensions;
        }

        @Override
        public int numberOfAxes() {
            return dimensions.length;
        }

        @Override
        public int axisLength(@Nonnegative int axis) {
            rangeCheck(axis, numberOfAxes());
            return dimensions[axis];
        }

        @Override
        public int numberOfElements() {
            int n = 1;
            for (int dimension : dimensions) {
                n *= dimension;
            }
            return n;
        }

        @Override
        public void checkPosition(@Nonnull Position pos) {
            requireNonNull(pos, "pos");
            if (numberOfAxes() != pos.numberOfAxes()) {
                throwIllegalArgument("%s has different number of axes than %s", pos, this);
            }
            for (int axis = 0; axis < dimensions.length; ++axis) {
                rangeCheck(pos.coordinate(axis), axisLength(axis));
            }
        }

        @Override
        public int elementIndex(@Nonnull Position pos) {
            requireNonNull(pos, "pos");
            if (numberOfAxes() != pos.numberOfAxes()) {
                throwIllegalArgument("%s has different number of axes than %s", pos, this);
            }
            int elementIndex = 0;
            int blockSize = 1;
            for (int axis = dimensions.length - 1; axis >= 0; axis--) {
                int coordinate = pos.coordinate(axis);
                int axisLength = axisLength(axis);
                rangeCheck(coordinate, axisLength);

                elementIndex += blockSize * coordinate;
                blockSize *= axisLength;
            }
            return elementIndex;
        }

        @Nonnull
        @Override
        public Stream<Position> positionStream() {
            return StreamSupport.stream(
                Spliterators.spliterator(iterator(), numberOfElements(), NONNULL | SIZED | SUBSIZED),
                false);
        }

        @Nonnull
        @Override
        public Shape slice(@Nonnegative int sliceAxis) {
            int origNumberOfAxes = numberOfAxes();
            rangeCheck(sliceAxis, origNumberOfAxes);
            if (origNumberOfAxes == 1) {
                throwIllegalState("Can't slice single-dimensional axis");
            }
            int[] newDimensions = new int[origNumberOfAxes - 1];
            int newAxis = 0;
            for (int origAxis = 0; origAxis < origNumberOfAxes; ++origAxis) {
                if (origAxis != sliceAxis) {
                    newDimensions[newAxis++] = dimensions[origAxis];
                }
            }
            return Shapes.shape(newDimensions);
        }

        @Override
        public Iterator<Position> iterator() {
            return new Positions.PositionIterator(this);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            DefaultShape that = (DefaultShape) o;

            return Arrays.equals(dimensions, that.dimensions);

        }

        @Override
        public int hashCode() {
            return Arrays.hashCode(dimensions);
        }

        @Override
        public String toString() {
            return "Shape" + Arrays.toString(dimensions);
        }
    }
}

