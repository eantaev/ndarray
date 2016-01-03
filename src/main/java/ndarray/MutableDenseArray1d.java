package ndarray;

/**
 * Date: 29.12.15
 * Time: 0:48
 *
 * @author Evgeny Antaev
 */
final class MutableDenseArray1d extends DenseArray1d implements MutableArray1d {
    MutableDenseArray1d(double[] data) {
        super(data);
    }

    @Override
    public MutableArray1d set(int i, double value) {
        this.data[i] = value;
        return this;
    }
}
