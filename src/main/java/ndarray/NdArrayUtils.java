package ndarray;

import javax.annotation.Nonnull;

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;

/**
 * Date: 02.01.16
 * Time: 17:47
 *
 * @author Evgeny Antaev
 */
public final class NdArrayUtils {
    public static void rangeCheck(int index, int length) {
        if (index < 0 || index >= length) {
            throw new IndexOutOfBoundsException(indexOutOfBoundsMsg(index, length));
        }
    }

    private static String indexOutOfBoundsMsg(int index, int length) {
        return "Index: " + index + ", Length: " + length;
    }

    public static boolean allNonNegative(int... values) {
        requireNonNull(values, "values");
        for (int d : values) {
            if (d < 0) {
                return false;
            }
        }
        return true;
    }

    public static void throwIllegalArgument(@Nonnull String messageTemplate, Object... args) {
        throw new IllegalArgumentException(format(requireNonNull(messageTemplate), args));
    }

    public static void throwIllegalState(@Nonnull String messageTemplate, Object... args) {
        throw new IllegalStateException(format(requireNonNull(messageTemplate), args));
    }
}
