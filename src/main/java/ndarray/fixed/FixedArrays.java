package ndarray.fixed;

/**
 * Date: 29.12.15
 * Time: 1:32
 *
 * @author Evgeny Antaev
 */
public final class FixedArrays {
    public static final Array1dFactory<ImmutableArray1d> immutable1d = new ImmutableArray1dFactory();
    public static final Array1dFactory<MutableArray1d> mutable1d = new MutableArray1dFactory();

    public static final Array2dFactory<MutableArray2d> mutable2d = new MutableArray2dFactory();
    public static final Array2dFactory<ImmutableArray2d> immutable2d = new ImmutableArray2dFactory();

    public static final Array3dFactory<MutableArray3d> mutable3d = new MutableArray3dFactory();
    public static final Array3dFactory<ImmutableArray3d> immutable3d = new ImmutableArray3dFactory();

    private FixedArrays() {
    }
}
