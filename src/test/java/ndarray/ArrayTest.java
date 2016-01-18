package ndarray;

import com.google.common.testing.NullPointerTester;
import ndarray.epoch.EpochArrayAllocator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static ndarray.Unpooled.expand;
import static ndarray.pos.Positions.position;
import static ndarray.shape.Shapes.shape;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

/**
 * Date: 17.01.2016
 * Time: 17:08
 *
 * @author Evgeny Antaev
 */
@RunWith(Parameterized.class)
public class ArrayTest {

    private static final double DELTA = 1e-8;

    private final ArrayFactory factory;

    public ArrayTest(ArrayFactory factory) {
        this.factory = factory;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
            {Unpooled.factory()},
            {EpochArrayAllocator.withInitialSize(1 << 10)}
        });
    }

    @Test
    public void nonnullFactoryMethodsParamsAreChecked() {
        new NullPointerTester().testAllPublicInstanceMethods(factory);
    }

    @Test
    public void wrappedArrayNonnullMethodsParamsAreChecked() {
        new NullPointerTester().testAllPublicInstanceMethods(factory.wrap(shape(2, 2, 1), 1, 2, 3, 4));
    }

    @Test
    public void copiedArrayNonnullMethodsParamsAreChecked() {
        new NullPointerTester().testAllPublicInstanceMethods(factory.copyOf(shape(2, 2, 1), 1, 2, 3, 4));
    }

    @Test
    public void constZero() {
        Array a = factory.constantZero(shape(3, 2));
        assertTrue(a.isConstantZero());
    }

    @Test(expected = IllegalStateException.class)
    public void constZeroIsImmutable() {
        Array a = factory.constantZero(shape(3, 2));
        a.set(position(0, 0), 0);
    }

    @Test
    public void zerosCreatesArrayWithGivenShape() {
        int numberOfItems = 4;
        int numberOfRows = 3;
        int numberOfColumns = 2;

        Array a = factory.create(shape(numberOfItems, numberOfRows, numberOfColumns));

        assertThat(a.shape(), equalTo(shape(numberOfItems, numberOfRows, numberOfColumns)));
    }

    @Test
    public void zerosCreatesArrayWithAllZeros() {
        int numberOfItems = 4;
        int numberOfRows = 3;
        int numberOfColumns = 2;

        Array a = factory.create(shape(numberOfItems, numberOfRows, numberOfColumns));

        for (int i = 0; i < numberOfItems; ++i) {
            for (int r = 0; r < numberOfRows; ++r) {
                for (int c = 0; c < numberOfColumns; ++c) {
                    assertThat(a.at(position(i, r, c)), equalTo(0d));
                }
            }
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void accessingNegativeIndexThrowsIndexOutOfBounds() {
        factory.create(shape(4, 3, 2)).at(position(0, 0, -1));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void accessingIndexGteLengthThrowsIndexOutOfBounds() {
        factory.create(shape(3, 3, 3)).at(position(0, 3, 0));
    }

    @Test
    public void copyOfCreatesArrayWithGivenShape() {
        int numberOfItems = 1;
        int numberOfRows = 3;
        int numberOfColumns = 2;

        Array a = factory.copyOf(shape(numberOfItems, numberOfRows, numberOfColumns), 1, 2, 3, 4, 5, 6);

        assertThat(a.shape(), equalTo(shape(numberOfItems, numberOfRows, numberOfColumns)));
    }

    @Test
    public void copyOfCreatesArrayWithSameValues() {
        int numberOfItems = 1;
        int numberOfRows = 3;
        int numberOfColumns = 2;
        double[] values = {1, 2, 3, 4, 5, 6};

        Array a = factory.copyOf(shape(numberOfItems, numberOfRows, numberOfColumns), values);

        int index = 0;
        for (Slider s = a.slider(); s.x() < numberOfItems; s.advanceX()) {
            for (s.y(0); s.y() < numberOfRows; s.advanceY()) {
                for (s.z(0); s.z() < numberOfColumns; s.advanceZ()) {
                    assertThat(a.at(s), equalTo(values[index++]));
                }
            }
        }
    }

    @Test
    public void copyOfCreatesArrayBackedByCopyOfGivenArray() {
        double[] values = {1};
        Array a = factory.copyOf(shape(1, 1, 1), values);

        assertThat(a.at(position(0, 0, 0)), equalTo(1d));

        values[0] = 10d;

        assertThat(a.at(position(0, 0, 0)), equalTo(1d));
    }

    @Test
    public void wrapCreatesArrayWithGivenShape() {
        int numberOfItems = 1;
        int numberOfRows = 3;
        int numberOfColumns = 2;

        Array a = factory.wrap(shape(numberOfItems, numberOfRows, numberOfColumns), 1, 2, 3, 4, 5, 6);

        assertThat(a.shape(), equalTo(shape(numberOfItems, numberOfRows, numberOfColumns)));
    }

    @Test
    public void wrapCreatesArrayWithSameValues() {
        int numberOfItems = 1;
        int numberOfRows = 3;
        int numberOfColumns = 2;
        double[] values = {1, 2, 3, 4, 5, 6};

        Array a = factory.wrap(shape(numberOfItems, numberOfRows, numberOfColumns), values);

        int index = 0;
        Slider s = a.shape().slider();
        for (int i = 0; i < numberOfItems; ++i) {
            s.x(i);
            for (int r = 0; r < numberOfRows; ++r) {
                s.y(r);
                for (int c = 0; c < numberOfColumns; ++c) {
                    s.z(c);
                    assertThat(a.at(s), equalTo(values[index++]));
                }
            }
        }
    }

    @Test
    public void wrapCreatesArrayBackedBySameArray() {
        double[] values = {1};
        Array a = factory.wrap(shape(1, 1, 1), values);

        assertThat(a.at(position(0, 0, 0)), equalTo(1d));

        values[0] = 10d;

        assertThat(a.at(position(0, 0, 0)), equalTo(10d));
    }

    @Test
    public void readValues() {
        double[] data = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};

        Array a = factory.wrap(shape(2, 3, 2), data);

        assertArrayEquals(data, a.readValues(), DELTA);
    }

    @Test
    public void copyOf() {
        double[] data = {
            1, 2,
            3, 4,
            5, 6,
            //---
            7, 8,
            9, 10,
            11, 12};

        Array a = factory.wrap(shape(2, 3, 2), data);

        assertThat(a, equalTo(factory.copyOf(a)));
    }

    @Test
    public void sliceOfConstantZeroIsConstantZero() {
        assertTrue(expand(factory.constantZero(shape(3, 4)), 1, 4).isConstantZero());
    }

    @Test
    public void slice2dArray() {
        Array a = factory.wrap(shape(3, 2),
            1, 2,
            3, 4,
            5, 6);

        assertThat(a.slice(0, 0), equalTo(factory.wrap(shape(2), 1, 2)));
        assertThat(a.slice(0, 1), equalTo(factory.wrap(shape(2), 3, 4)));
        assertThat(a.slice(0, 2), equalTo(factory.wrap(shape(2), 5, 6)));

        assertThat(a.slice(1, 0), equalTo(factory.wrap(shape(3), 1, 3, 5)));
        assertThat(a.slice(1, 1), equalTo(factory.wrap(shape(3), 2, 4, 6)));
    }

    @Test
    public void slice3dArray() {
        Array a = factory.wrap(shape(2, 3, 2),
            1, 2,
            3, 4,
            5, 6,
            //---
            7, 8,
            9, 10,
            11, 12);

        assertThat(a.slice(0, 0), equalTo(factory.wrap(shape(3, 2),
            1, 2,
            3, 4,
            5, 6)));
        assertThat(a.slice(0, 1), equalTo(factory.wrap(shape(3, 2),
            7, 8,
            9, 10,
            11, 12)));

        assertThat(a.slice(1, 0), equalTo(factory.wrap(shape(2, 2),
            1, 2,
            7, 8)));
        assertThat(a.slice(1, 1), equalTo(factory.wrap(shape(2, 2),
            3, 4,
            9, 10)));
        assertThat(a.slice(1, 2), equalTo(factory.wrap(shape(2, 2),
            5, 6,
            11, 12)));

        assertThat(a.slice(2, 0), equalTo(factory.wrap(shape(2, 3),
            1, 3, 5,
            7, 9, 11)));
        assertThat(a.slice(2, 1), equalTo(factory.wrap(shape(2, 3),
            2, 4, 6,
            8, 10, 12)));
    }

    @Test
    public void sliceOfSlice() {
        Array a = factory.wrap(shape(2, 3, 2),
            1, 2,
            3, 4,
            5, 6,
            //---
            7, 8,
            9, 10,
            11, 12);

        assertThat(a.slice(0, 0).slice(0, 0), equalTo(factory.wrap(shape(2), 1, 2)));
        assertThat(a.slice(0, 1).slice(1, 1), equalTo(factory.wrap(shape(3), 8, 10, 12)));
    }

    @Test
    public void sliceIsModifiable() {
        Array a = factory.wrap(shape(3, 2),
            1, 2,
            3, 4,
            5, 6);

        Array slice = a.slice(0, 1);
        slice.set(position(0), -3);

        assertThat(slice, equalTo(factory.wrap(shape(2), -3, 4)));
    }

    @Test
    public void sliceIsView() {
        Array a = factory.wrap(shape(3, 2),
            1, 2,
            3, 4,
            5, 6);

        a.slice(0, 1).set(position(0), -3);

        assertThat(a, equalTo(factory.wrap(shape(3, 2),
            1, 2,
            -3, 4,
            5, 6)));
    }

    @Test
    public void sliceReadValues() {
        double[] data = {
            1, 2,
            3, 4,
            5, 6,
            //---
            7, 8,
            9, 10,
            11, 12};

        Array a = factory.wrap(shape(2, 3, 2), data).slice(1, 2);

        assertArrayEquals(new double[]{5, 6, 11, 12}, a.readValues(), DELTA);
    }

    @Test
    public void sliceCopyOf() {
        double[] data = {
            1, 2,
            3, 4,
            5, 6,
            //---
            7, 8,
            9, 10,
            11, 12};

        Array a = factory.wrap(shape(2, 3, 2), data).slice(1, 2);

        assertThat(a, equalTo(factory.copyOf(a)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void expandAlongNegativeAxisIsMeaningless() {
        expand(factory.create(shape(3)), -1, 1);
    }

    @Test
    public void expandingAlongZeroLengthAxisResultsInConstantZero() {
        assertTrue(expand(factory.create(shape(3)), 1, 0).isConstantZero());
    }

    @Test
    public void expandingConstantZeroResultsInConstantZero() {
        assertTrue(expand(factory.constantZero(shape(3, 2)), 1, 0).isConstantZero());
    }

    @Test
    public void expandArray1dAlongAxis0() {
        Array a = expand(factory.wrap(shape(3), 1, 2, 3), 0, 2);

        assertThat(a,
            equalTo(Unpooled.wrap(shape(2, 3),
                1, 2, 3,
                1, 2, 3)));
    }

    @Test
    public void expandArray1dAlongAxis1() {
        Array a = expand(factory.wrap(shape(3), 1, 2, 3), 1, 2);

        assertThat(a,
            equalTo(Unpooled.wrap(shape(3, 2),
                1, 1,
                2, 2,
                3, 3)));
    }

    @Test
    public void expandArray2dAlongAxis0() {
        Array a = expand(
            factory.wrap(shape(3, 2),
                1, 2,
                3, 4,
                5, 6),
            0, 2);

        assertThat(a,
            equalTo(Unpooled.wrap(shape(2, 3, 2),
                1, 2,
                3, 4,
                5, 6,
                //--
                1, 2,
                3, 4,
                5, 6)));
    }

    @Test
    public void expandArray2dAlongAxis1() {
        Array a = expand(
            factory.wrap(shape(3, 2),
                1, 2,
                3, 4,
                5, 6),
            1, 2);

        assertThat(a,
            equalTo(Unpooled.wrap(shape(3, 2, 2),
                1, 2,
                1, 2,
                //--
                3, 4,
                3, 4,
                //--
                5, 6,
                5, 6)));
    }

    @Test
    public void expandArray2dAlongAxis2() {
        Array a = expand(
            factory.wrap(shape(3, 2),
                1, 2,
                3, 4,
                5, 6),
            2, 2);

        assertThat(a,
            equalTo(Unpooled.wrap(shape(3, 2, 2),
                1, 1,
                2, 2,
                //--
                3, 3,
                4, 4,
                //--
                5, 5,
                6, 6)));
    }

    @Test
    public void expansionIsModifiable() {
        Array a = factory.wrap(shape(3), 1, 2, 3);

        Array expansion = expand(a, 0, 2);
        expansion.set(position(1, 1), -2);

        assertThat(expansion,
            equalTo(factory.wrap(shape(2, 3),
                1, -2, 3,
                1, -2, 3)));
    }

    @Test
    public void expansionIsView() {
        Array a = factory.wrap(shape(3), 1, 2, 3);

        Array expansion = expand(a, 0, 2);
        expansion.set(position(1, 1), -2);

        assertThat(a, equalTo(factory.wrap(shape(3), 1, -2, 3)));
    }


    @Test
    public void expansionReadValues() {
        double[] data = {1, 2, 3};

        Array a = expand(factory.wrap(shape(3), data), 0, 2);

        assertArrayEquals(new double[]{1, 2, 3, 1, 2, 3}, a.readValues(), DELTA);
    }

    @Test
    public void expansionCopyOf() {
        double[] data = {1, 2, 3};

        Array a = expand(factory.wrap(shape(3), data), 0, 2);

        assertThat(a, equalTo(factory.copyOf(a)));
    }
}
