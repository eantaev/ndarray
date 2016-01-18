package ndarray.pos;

import ndarray.Position;
import ndarray.Shape;

import javax.annotation.Nonnull;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static java.util.Objects.requireNonNull;

/**
 * Date: 19.01.16
 * Time: 1:09
 *
 * @author Evgeny Antaev
 */
public class ShapeIterator implements Iterator<Position> {
    @Nonnull
    private final Shape shape;
    @Nonnull
    private final DefaultPosition position;

    public ShapeIterator(@Nonnull Shape shape) {
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
                position.move(axis, 0);
            } else {
                position.move(axis, position.coordinate(axis) + 1);
                return position;
            }
        }
        throw new NoSuchElementException("no more positions");
    }
}
