package ndarray;

/**
 * Date: 16.01.16
 * Time: 13:01
 *
 * @author Evgeny Antaev
 */
@FunctionalInterface
public interface ArrayConsumer {
    void accept(Position position, double value);
}
