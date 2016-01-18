package ndarray.pos;

import static ndarray.NdArrayUtils.checkNonNegative;

/**
 * Date: 19.01.16
 * Time: 0:57
 *
 * @author Evgeny Antaev
 */
class Position1 extends AbstractPosition {
    int x;

    Position1(int x) {
        checkNonNegative(x, "x < 0");
        this.x = x;
    }

    @Override
    public int numberOfAxes() {
        return 1;
    }

    @Override
    public int x() {
        return x;
    }

    @Override
    public int coordinate(int axis) {
        if (axis == 0)
            return x;
        throw new IllegalArgumentException("Axis " + axis + " is out of [0, " + numberOfAxes() + ')');
    }
}
