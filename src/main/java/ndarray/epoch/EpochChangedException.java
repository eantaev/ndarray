package ndarray.epoch;

/**
 * Date: 17.01.16
 * Time: 20:59
 *
 * @author Evgeny Antaev
 */
public final class EpochChangedException extends IllegalStateException {
    public EpochChangedException() {
        this("Epoch has changed. This object is no longer valid.");
    }

    public EpochChangedException(String message) {
        super(message);
    }

    public EpochChangedException(String messageTemplate, Object... args) {
        this(String.format(messageTemplate, args));
    }
}
