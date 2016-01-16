package ndarray;

import static ndarray.NdArrayUtils.rangeCheck;

/**
 * Date: 12.01.16
 * Time: 0:04
 *
 * @author Evgeny Antaev
 */
public abstract class AbstractArray1d implements Array1d {

    public abstract double atUnchecked(int index);

    @Override
    public final double at(int index) {
        rangeCheck(index, length());
        return atUnchecked(index);
    }

    @Override
    public int hashCode() {
        int result = 1;
        for (int i = 0; i < length(); ++i) {
            long bits = Double.doubleToLongBits(atUnchecked(i));
            result = 31 * result + (int) (bits ^ (bits >>> 32));
        }
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof Array1d)) return false;

        Array1d a = (Array1d) o;
        int length = length();

        if (length != a.length()) return false;

        if (o instanceof AbstractArray1d) {
            AbstractArray1d aa = (AbstractArray1d) a;
            for (int i = 0; i < length; ++i)
                if (atUnchecked(i) != aa.atUnchecked(i))
                    return false;
        } else {
            for (int i = 0; i < length; ++i)
                if (atUnchecked(i) != a.at(i))
                    return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(8 * length());
        sb.append('[');
        if (length() > 0) {
            sb.append(atUnchecked(0));
            for (int i = 1; i < length(); ++i) {
                sb.append(", ");
                sb.append(atUnchecked(i));
            }
        }
        sb.append(']');
        return sb.toString();
    }
}
