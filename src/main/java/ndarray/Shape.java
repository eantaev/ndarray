package ndarray;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import java.util.stream.Stream;

/**
 * Date: 16.01.16
 * Time: 13:00
 *
 * @author Evgeny Antaev
 */
public interface Shape extends Iterable<Position> {

    int numberOfAxes();

    int axisLength(@Nonnegative int axis);

    int numberOfElements();

    void checkPosition(@Nonnull Position pos);

    int elementIndex(@Nonnull Position pos);

    @Nonnull
    Stream<Position> positionStream();

    @Nonnull
    Shape slice(@Nonnegative int axis);
}

