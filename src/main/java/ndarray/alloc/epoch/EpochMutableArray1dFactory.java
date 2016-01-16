package ndarray.alloc.epoch;

import ndarray.Array1dFactory;
import ndarray.MutableArray1d;
import ndarray.NdArrays;

import javax.annotation.Nonnull;
import java.util.function.IntToDoubleFunction;

import static java.util.Objects.requireNonNull;

/**
 * Date: 14.01.16
 * Time: 0:49
 *
 * @author Evgeny Antaev
 */
final class EpochMutableArray1dFactory implements Array1dFactory<MutableArray1d> {

    private EpochArrayAllocator alloc;

    EpochMutableArray1dFactory(EpochArrayAllocator alloc) {
        this.alloc = requireNonNull(alloc, "alloc");
    }

    @Override
    public MutableArray1d zeros(int length) {
        return all(length, 0);
    }

    @Override
    public MutableArray1d all(int length, double value) {
        if (length < 0) {
            throw new IllegalArgumentException("length < 0: " + length);
        }
        EpochMutableArray1d a = new EpochMutableArray1d(alloc, alloc.allocateBlock(length), length);
        a.fill(value);
        return a;
    }

    @Override
    public MutableArray1d copyOf(double... values) {
        requireNonNull(values, "values");
        EpochMutableArray1d a = new EpochMutableArray1d(
            alloc, alloc.allocateBlock(values.length), values.length);
        a.writeValues(values);
        return a;
    }

    @Override
    public MutableArray1d wrap(double... values) {
        return NdArrays.mutable1d.wrap(values);
    }

    @Override
    public MutableArray1d generate(int length, @Nonnull IntToDoubleFunction generator) {
        return zeros(length).fill(requireNonNull(generator, "generator"));
    }
}
