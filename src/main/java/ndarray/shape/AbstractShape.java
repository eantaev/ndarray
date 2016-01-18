package ndarray.shape;

import ndarray.Position;
import ndarray.Shape;
import ndarray.Slider;
import ndarray.pos.Positions;
import ndarray.pos.ShapeIterator;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import java.util.Iterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static java.util.Objects.requireNonNull;
import static java.util.Spliterator.*;
import static ndarray.NdArrayUtils.*;

/**
 * Date: 19.01.16
 * Time: 1:15
 *
 * @author Evgeny Antaev
 */
abstract class AbstractShape implements Shape {

    @Override
    public int numberOfElements() {
        int n = 1;
        int numberOfAxes = numberOfAxes();
        for (int axis = 0; axis < numberOfAxes; ++axis) {
            n *= axisLength(axis);
        }
        return n;
    }

    @Override
    public void checkPosition(@Nonnull Position pos) {
        requireNonNull(pos, "pos");
        int numberOfAxes = numberOfAxes();
        if (numberOfAxes != pos.numberOfAxes()) {
            throwIllegalArgument("%s has different number of axes than %s", pos, this);
        }
        for (int axis = 0; axis < numberOfAxes; ++axis) {
            rangeCheck(pos.coordinate(axis), axisLength(axis));
        }
    }

    @Override
    public int elementIndex(@Nonnull Position pos) {
        requireNonNull(pos, "pos");
        int numberOfAxes = numberOfAxes();
        if (numberOfAxes != pos.numberOfAxes()) {
            throwIllegalArgument("%s has different number of axes than %s", pos, this);
        }
        int elementIndex = 0;
        int blockSize = 1;
        for (int axis = numberOfAxes - 1; axis >= 0; axis--) {
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
    public Slider slider() {
        return Positions.slider(this);
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
                newDimensions[newAxis++] = axisLength(origAxis);
            }
        }
        return Shapes.shape(newDimensions);
    }

    @Nonnull
    @Override
    public Shape insert(@Nonnegative int beforeIndex, @Nonnegative int axisLength) {
        int origNumberOfAxes = numberOfAxes();
        if (beforeIndex < 0) {
            throwIllegalArgument("beforeIndex < 0");
        }
        if (beforeIndex > origNumberOfAxes) {
            throwIllegalArgument("beforeIndex %d > %d numberOfAxes", beforeIndex, origNumberOfAxes);
        }
        if (axisLength < 0) {
            throwIllegalArgument("axisLength < 0");
        }
        int[] newDimensions = new int[origNumberOfAxes + 1];
        int oldAxis = 0;
        for (int newAxis = 0; newAxis < newDimensions.length; ++newAxis) {
            if (newAxis == beforeIndex) {
                newDimensions[newAxis] = axisLength;
            } else {
                newDimensions[newAxis] = axisLength(oldAxis++);
            }
        }
        return Shapes.shape(newDimensions);
    }

    @Override
    public Iterator<Position> iterator() {
        return new ShapeIterator(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof Shape)) return false;

        Shape that = (Shape) o;

        int numberOfAxes = numberOfAxes();
        if (numberOfAxes != that.numberOfAxes()) return false;

        for (int axis = 0; axis < numberOfAxes; ++axis) {
            if (axisLength(axis) != that.axisLength(axis))
                return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = 1;
        int numberOfAxes = numberOfAxes();
        for (int axis = 0; axis < numberOfAxes; ++axis) {
            result = 31 * result + axisLength(axis);
        }
        return result;

    }

    @Override
    public String toString() {
        int numberOfAxes = numberOfAxes();
        if (numberOfAxes == 0) {
            return "Shape[/]";
        }
        StringBuilder sb = new StringBuilder(7 + 4 * numberOfAxes);
        sb.append("Shape[");
        sb.append(axisLength(0));
        for (int axis = 1; axis < numberOfAxes; ++axis) {
            sb.append(", ");
            sb.append(axisLength(axis));
        }
        sb.append(']');
        return sb.toString();
    }
}
