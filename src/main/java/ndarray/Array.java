package ndarray;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

/**
 * Date: 16.01.2016
 * Time: 12:42
 *
 * @author Evgeny Antaev
 */
public interface Array {
    @Nonnull
    Shape shape();

    @Nonnull
    Slider slider();

    double at(@Nonnull Position p);

    void set(@Nonnull Position position, double value);

    boolean isConstantZero();

    @Nonnull
    Array slice(@Nonnegative int axis, @Nonnegative int coordinate);

    void forEach(@Nonnull ArrayConsumer consumer);

    @Nonnull
    double[] readValues();

    @Nonnegative
    int readValues(@Nonnull double[] buffer);

    @Nonnegative
    int readValues(@Nonnull double[] buffer, @Nonnegative int offset);
}
