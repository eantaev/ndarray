package ndarray;

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
}
