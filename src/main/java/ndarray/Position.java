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

    default int x() {
        return coordinate(0);
    }

    default int y() {
        return coordinate(1);
    }

    default int z() {
        return coordinate(2);
    }
}

