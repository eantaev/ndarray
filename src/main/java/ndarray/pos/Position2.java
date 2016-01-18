package ndarray.pos;

import static ndarray.NdArrayUtils.checkNonNegative;

/**
 * Date: 19.01.16
 * Time: 0:56
 *
 * @author Evgeny Antaev
 */
class Position2 extends AbstractPosition {
    int x;
    int y;

    Position2(int x, int y) {
        checkNonNegative(x, "x < 0");
        checkNonNegative(y, "y < 0");
        this.x = x;
        this.y = y;
    }

    @Override
    public int numberOfAxes() {
        return 2;
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
    public int coordinate(int axis) {
        switch (axis) {
            case 0:
                return x;
            case 1:
                return y;
        }
        throw new IllegalArgumentException("Axis " + axis + " is out of [0, " + numberOfAxes() + ')');
    }
}
