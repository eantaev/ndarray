package ndarray.pos;

import static ndarray.NdArrayUtils.checkNonNegative;

/**
 * Date: 19.01.16
 * Time: 0:57
 *
 * @author Evgeny Antaev
 */
class Position3 extends AbstractPosition {
    int x;
    int y;
    int z;

    Position3(int x, int y, int z) {
        checkNonNegative(x, "x < 0");
        checkNonNegative(y, "y < 0");
        checkNonNegative(z, "z < 0");
        this.z = z;
        this.x = x;
        this.y = y;
    }

    @Override
    public int numberOfAxes() {
        return 3;
    }

    @Override
    public int x() {
        return x;
    }

    @Override
    public int y() {
        return y;
    }

    @Override
    public int z() {
        return z;
    }

    @Override
    public int coordinate(int axis) {
        switch (axis) {
            case 0:
                return x;
            case 1:
                return y;
            case 2:
                return z;
        }
        throw new IllegalArgumentException("Axis " + axis + " is out of [0, " + numberOfAxes() + ')');
    }
}
