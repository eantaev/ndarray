package ndarray;

import ndarray.epoch.EpochArrayAllocator;
import ndarray.epoch.EpochChangedException;
import org.junit.Test;

import static ndarray.shape.Shapes.shape;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Date: 17.01.16
 * Time: 20:37
 *
 * @author Evgeny Antaev
 */
public class EpochAllocatorTest {
    @Test(expected = IllegalArgumentException.class)
    public void initialSizeShouldBeGt0() {
        EpochArrayAllocator.withInitialSize(-1);
    }

    @Test
    public void constantZeroDoesNotConsumeAllocatedSpace() {
        EpochArrayAllocator alloc = EpochArrayAllocator.withInitialSize(1 << 2);

        alloc.constantZero(shape(100, 50));

        assertThat(alloc.allocated(), equalTo(0));
    }

    @Test
    public void wrapDoesNotConsumeAllocatedSpace() {
        EpochArrayAllocator alloc = EpochArrayAllocator.withInitialSize(1 << 2);

        alloc.wrap(shape(3, 2), 1, 2, 3, 4, 5, 6);

        assertThat(alloc.allocated(), equalTo(0));
    }

    @Test
    public void createConsumesNumberOfElements() {
        EpochArrayAllocator alloc = EpochArrayAllocator.withInitialSize(1 << 4);
        Shape shape = shape(3, 2);

        alloc.create(shape);

        assertThat(alloc.allocated(), equalTo(shape.numberOfElements()));
    }

    @Test
    public void copyOfConsumesNumberOfElements() {
        EpochArrayAllocator alloc = EpochArrayAllocator.withInitialSize(1 << 4);
        Shape shape = shape(3, 2);

        alloc.copyOf(shape, 1, 2, 3, 4, 5, 6);

        assertThat(alloc.allocated(), equalTo(shape.numberOfElements()));
    }

    @Test
    public void bufferGrows() {
        int initialSize = 4;
        EpochArrayAllocator alloc = EpochArrayAllocator.withInitialSize(initialSize);

        assertThat(alloc.bufferSize(), equalTo(initialSize));

        alloc.create(shape(3, 2));

        assertTrue(alloc.bufferSize() > initialSize);
    }

    @Test
    public void newEpochReclaimsAllocatedSpace() {
        EpochArrayAllocator alloc = EpochArrayAllocator.withInitialSize(8);

        assertThat(alloc.allocated(), equalTo(0));

        alloc.create(shape(3, 2));

        assertThat(alloc.allocated(), equalTo(6));

        alloc.newEpoch();

        assertThat(alloc.allocated(), equalTo(0));
    }

    @Test
    public void bufferDoesNotGrowUnnecessarily() {
        int numberOfEpochs = 1000;
        int numberOfArrays = 1000;
        Shape shape = shape(10, 10, 10);
        int initialSize = shape.numberOfElements() * numberOfArrays;
        EpochArrayAllocator alloc = EpochArrayAllocator.withInitialSize(initialSize);

        for (int epoch = 0; epoch < numberOfEpochs; ++epoch) {
            alloc.newEpoch();
            allocateArrays(alloc, shape, numberOfArrays);

            assertThat(alloc.bufferSize(), equalTo(initialSize));
        }
    }

    @Test
    public void createInitializedArrayWithZeros() {
        EpochArrayAllocator alloc = EpochArrayAllocator.withInitialSize(4);

        alloc.copyOf(shape(4), 1, 2, 3, 4);

        alloc.newEpoch();

        assertThat(alloc.create(shape(4)), equalTo(Unpooled.constantZero(shape(4))));
    }

    @Test(expected = EpochChangedException.class)
    public void newEpochInvalidatesAllocatedArrays() {
        EpochArrayAllocator alloc = EpochArrayAllocator.withInitialSize(8);
        Array a = alloc.create(shape(3, 2));

        alloc.newEpoch();

        a.shape();
    }

    @Test(expected = EpochChangedException.class)
    public void destroyInvalidatesAllocatedArrays() {
        EpochArrayAllocator alloc = EpochArrayAllocator.withInitialSize(16);
        Array a = alloc.create(shape(3, 2));

        alloc.destroy();

        a.shape();
    }

    @Test(expected = RuntimeException.class)
    public void afterDestroyAllocatorIsUnusable() {
        EpochArrayAllocator alloc = EpochArrayAllocator.withInitialSize(16);
        alloc.destroy();
        alloc.create(shape(2, 2));
    }

    private static void allocateArrays(EpochArrayAllocator alloc, Shape shape, int numberOfArrays) {
        for (int count = 0; count < numberOfArrays; ++count) {
            alloc.createNoInitialize(shape);
        }
    }
}
