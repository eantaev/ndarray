package ndarray;

import com.google.common.testing.NullPointerTester;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Date: 31.12.15
 * Time: 0:51
 *
 * @author Evgeny Antaev
 */
@RunWith(Parameterized.class)
public class Array3dTest<A extends Array3d> {

    private final Array3dFactory<A> factory;

    public Array3dTest(Array3dFactory<A> factory) {
        this.factory = factory;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
            {NdArrays.immutable3d}, {NdArrays.mutable3d}
        });
    }

    @Test
    public void nonnullFactoryMethodsParamsAreChecked() {
        new NullPointerTester().testAllPublicInstanceMethods(factory);
    }

    @Test
    public void wrappedArrayNonnullMethodsParamsAreChecked() {
        new NullPointerTester().testAllPublicInstanceMethods(factory.wrap(2, 2, 1, new double[]{1, 2, 3, 4}));
    }

    @Test
    public void copiedArrayNonnullMethodsParamsAreChecked() {
        new NullPointerTester().testAllPublicInstanceMethods(factory.copyOf(2, 2, 1, new double[]{1, 2, 3, 4}));
    }

    @Test
    public void zerosCreatesArrayWithGivenShape() {
        int numberOfItems = 4;
        int numberOfRows = 3;
        int numberOfColumns = 2;

        A a = factory.zeros(numberOfItems, numberOfRows, numberOfColumns);

        assertThat(a.numberOfRows(), equalTo(numberOfRows));
        assertThat(a.numberOfColumns(), equalTo(numberOfColumns));
    }

    @Test
    public void zerosCreatesArrayWithAllZeros() {
        int numberOfItems = 4;
        int numberOfRows = 3;
        int numberOfColumns = 2;

        Array3d a = factory.zeros(numberOfItems, numberOfRows, numberOfColumns);

        for (int i = 0; i < numberOfItems; ++i) {
            for (int r = 0; r < numberOfRows; ++r) {
                for (int c = 0; c < numberOfColumns; ++c) {
                    assertThat(a.at(i, r, c), equalTo(0d));
                }
            }
        }
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void accessingNegativeIndexThrowsIndexOutOfBounds() {
        factory.zeros(4, 3, 2).at(0, 0, -1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void accessingIndexGteLengthThrowsIndexOutOfBounds() {
        factory.zeros(3, 3, 3).at(0, 3, 0);
    }

    @Test
    public void copyOfCreatesArrayWithGivenShape() {
        int numberOfItems = 1;
        int numberOfRows = 3;
        int numberOfColumns = 2;

        A a = factory.copyOf(numberOfItems, numberOfRows, numberOfColumns, new double[]{1, 2, 3, 4, 5, 6});

        assertThat(a.numberOfItems(), equalTo(numberOfItems));
        assertThat(a.numberOfRows(), equalTo(numberOfRows));
        assertThat(a.numberOfColumns(), equalTo(numberOfColumns));
    }

    @Test
    public void copyOfCreatesArrayWithSameValues() {
        int numberOfItems = 1;
        int numberOfRows = 3;
        int numberOfColumns = 2;
        double[] values = {1, 2, 3, 4, 5, 6};

        Array3d a = factory.copyOf(numberOfItems, numberOfRows, numberOfColumns, values);

        int index = 0;
        for (int i = 0; i < numberOfItems; ++i) {
            for (int r = 0; r < numberOfRows; ++r) {
                for (int c = 0; c < numberOfColumns; ++c) {
                    assertThat(a.at(i, r, c), equalTo(values[index++]));
                }
            }
        }
    }

    @Test
    public void copyOfCreatesArrayBackedByCopyOfGivenArray() {
        double[] values = {1};
        Array3d a = factory.copyOf(1, 1, 1, values);

        assertThat(a.at(0, 0, 0), equalTo(1d));

        values[0] = 10d;

        assertThat(a.at(0, 0, 0), equalTo(1d));
    }

    @Test
    public void wrapCreatesArrayWithGivenShape() {
        int numberOfItems = 1;
        int numberOfRows = 3;
        int numberOfColumns = 2;

        A a = factory.wrap(numberOfItems, numberOfRows, numberOfColumns, new double[]{1, 2, 3, 4, 5, 6});

        assertThat(a.numberOfItems(), equalTo(numberOfItems));
        assertThat(a.numberOfRows(), equalTo(numberOfRows));
        assertThat(a.numberOfColumns(), equalTo(numberOfColumns));
    }

    @Test
    public void wrapCreatesArrayWithSameValues() {
        int numberOfItems = 1;
        int numberOfRows = 3;
        int numberOfColumns = 2;
        double[] values = {1, 2, 3, 4, 5, 6};

        Array3d a = factory.wrap(numberOfItems, numberOfRows, numberOfColumns, values);

        int index = 0;
        for (int i = 0; i < numberOfItems; ++i) {
            for (int r = 0; r < numberOfRows; ++r) {
                for (int c = 0; c < numberOfColumns; ++c) {
                    assertThat(a.at(i, r, c), equalTo(values[index++]));
                }
            }
        }
    }

    @Test
    public void wrapCreatesArrayBackedBySameArray() {
        double[] values = {1};
        Array3d a = factory.wrap(1, 1, 1, values);

        assertThat(a.at(0, 0, 0), equalTo(1d));

        values[0] = 10d;

        assertThat(a.at(0, 0, 0), equalTo(10d));
    }

    @Test
    public void generateCreatesArrayWithGivenShape() {
        int numberOfItems = 4;
        int numberOfRows = 3;
        int numberOfColumns = 2;

        A a = factory.generate(numberOfItems, numberOfRows, numberOfColumns, (i, r, c) -> i + r + c);

        assertThat(a.numberOfRows(), equalTo(numberOfRows));
        assertThat(a.numberOfColumns(), equalTo(numberOfColumns));
    }

    @Test
    public void generateFillsArrayWithFunctionValues() {
        int numberOfItems = 4;
        int numberOfRows = 3;
        int numberOfColumns = 2;

        A a = factory.generate(numberOfItems, numberOfRows, numberOfColumns,
            (i, r, c) -> i * (numberOfRows * numberOfColumns) + r * numberOfColumns + c);

        int value = 0;
        for (int i = 0; i < numberOfItems; ++i) {
            for (int r = 0; r < numberOfRows; ++r) {
                for (int c = 0; c < numberOfColumns; ++c) {
                    assertThat(a.at(i, r, c), equalTo((double) value++));
                }
            }
        }
    }
}
