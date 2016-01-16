package ndarray.alloc.epoch;

import ndarray.Array1dFactory;
import ndarray.ImmutableArray1d;
import ndarray.NdArrays;

import javax.annotation.Nonnull;
import java.util.function.IntToDoubleFunction;

import static java.util.Objects.requireNonNull;

/**
 * Date: 14.01.16
 * Time: 0:50
 *
 * @author Evgeny Antaev
 */
final class EpochImmutableArray1dFactory implements Array1dFactory<ImmutableArray1d> {

    private EpochArrayAllocator alloc;

    public EpochImmutableArray1dFactory(EpochArrayAllocator alloc) {
        this.alloc = alloc;
    }

    @Override
    public ImmutableArray1d zeros(int length) {
        return NdArrays.immutable1d.zeros(length);
    }

    @Override
    public ImmutableArray1d all(int length, double value) {
        return NdArrays.immutable1d.all(length, value);
    }

    @Override
    public ImmutableArray1d copyOf(double... values) {
        requireNonNull(values, "values");
        EpochImmutableArray1d a = new EpochImmutableArray1d(
            alloc, alloc.allocateBlock(values.length), values.length);
        a.writeValues(values);
        return a;
    }

    @Override
    public ImmutableArray1d wrap(double... values) {
        return NdArrays.immutable1d.wrap(values);
    }

    @Override
    public ImmutableArray1d generate(int length, @Nonnull IntToDoubleFunction generator) {
        return NdArrays.immutable1d.generate(length, generator);
    }
}
