package ndarray;

/**
 * Date: 16.01.16
 * Time: 13:00
 *
 * @author Evgeny Antaev
 */
public interface Position {
    int numberOfAxes();

    int coordinate(int axis);
}

