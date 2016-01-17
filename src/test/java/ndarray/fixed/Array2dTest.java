package ndarray.fixed;

import com.google.common.testing.NullPointerTester;
import ndarray.fixed.Array2d;
import ndarray.fixed.Array2dFactory;
import ndarray.fixed.FixedArrays;
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
public class Array2dTest<A extends Array2d> {

    private final Array2dFactory<A> factory;

    public Array2dTest(Array2dFactory<A> factory) {
        this.factory = factory;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
            {FixedArrays.immutable2d}, {FixedArrays.mutable2d}
        });
    }

    @Test
    public void nonnullFactoryMethodsParamsAreChecked() {
        new NullPointerTester().testAllPublicInstanceMethods(factory);
    }

    @Test
    public void wrappedArrayNonnullMethodsParamsAreChecked() {
        new NullPointerTester().testAllPublicInstanceMethods(factory.wrap(2, 2, new double[]{1, 2, 3, 4}));
    }

    @Test
    public void copiedArrayNonnullMethodsParamsAreChecked() {
        new NullPointerTester().testAllPublicInstanceMethods(factory.copyOf(2, 2, new double[]{1, 2, 3, 4}));
    }

    @Test
    public void zerosCreatesArrayWithGivenShape() {
        int numberOfRows = 3;
        int numberOfColumns = 2;

        A a = factory.zeros(numberOfRows, numberOfColumns);

        assertThat(a.numberOfRows(), equalTo(numberOfRows));
        assertThat(a.numberOfColumns(), equalTo(numberOfColumns));
    }

    @Test
    public void zerosCreatesArrayWithAllZeros() {
        int numberOfRows = 3;
        int numberOfColumns = 2;

        Array2d a = factory.zeros(numberOfRows, numberOfColumns);

        for (int i = 0; i < numberOfRows; ++i) {
            for (int j = 0; j < numberOfColumns; ++j) {
                assertThat(a.at(i, j), equalTo(0d));
            }
        }
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void accessingNegativeIndexThrowsIndexOutOfBounds() {
        factory.zeros(3, 2).at(0, -1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void accessingIndexGteLengthThrowsIndexOutOfBounds() {
        factory.zeros(3, 3).at(3, 0);
    }

    @Test
    public void copyOfCreatesArrayWithGivenShape() {
        int numberOfRows = 3;
        int numberOfColumns = 2;

        A a = factory.copyOf(numberOfRows, numberOfColumns, new double[]{1, 2, 3, 4, 5, 6});

        assertThat(a.numberOfRows(), equalTo(numberOfRows));
        assertThat(a.numberOfColumns(), equalTo(numberOfColumns));
    }

    @Test
    public void copyOfCreatesArrayWithSameValues() {
        int numberOfRows = 3;
        int numberOfColumns = 2;
        double[] values = {1, 2, 3, 4, 5, 6};

        Array2d a = factory.copyOf(numberOfRows, numberOfColumns, values);

        int index = 0;
        for (int r = 0; r < numberOfRows; ++r) {
            for (int c = 0; c < numberOfColumns; ++c) {
                assertThat(a.at(r, c), equalTo(values[index++]));
            }
        }
    }

    @Test
    public void copyOfCreatesArrayBackedByCopyOfGivenArray() {
        double[] values = {1};
        Array2d a = factory.copyOf(1, 1, values);

        assertThat(a.at(0, 0), equalTo(1d));

        values[0] = 10d;

        assertThat(a.at(0, 0), equalTo(1d));
    }

    @Test
    public void wrapCreatesArrayWithGivenShape() {
        int numberOfRows = 3;
        int numberOfColumns = 2;

        A a = factory.wrap(numberOfRows, numberOfColumns, new double[]{1, 2, 3, 4, 5, 6});

        assertThat(a.numberOfRows(), equalTo(numberOfRows));
        assertThat(a.numberOfColumns(), equalTo(numberOfColumns));
    }

    @Test
    public void wrapCreatesArrayWithSameValues() {
        int numberOfRows = 3;
        int numberOfColumns = 2;
        double[] values = {1, 2, 3, 4, 5, 6};

        Array2d a = factory.wrap(numberOfRows, numberOfColumns, values);

        int index = 0;
        for (int r = 0; r < numberOfRows; ++r) {
            for (int c = 0; c < numberOfColumns; ++c) {
                assertThat(a.at(r, c), equalTo(values[index++]));
            }
        }
    }

    @Test
    public void wrapCreatesArrayBackedBySameArray() {
        double[] values = {1};
        Array2d a = factory.wrap(1, 1, values);

        assertThat(a.at(0, 0), equalTo(1d));

        values[0] = 10d;

        assertThat(a.at(0, 0), equalTo(10d));
    }

    @Test
    public void generateCreatesArrayWithGivenShape() {
        int numberOfRows = 3;
        int numberOfColumns = 2;

        A a = factory.generate(numberOfRows, numberOfColumns, (r, c) -> r * numberOfColumns + c);

        assertThat(a.numberOfRows(), equalTo(numberOfRows));
        assertThat(a.numberOfColumns(), equalTo(numberOfColumns));
    }

    @Test
    public void generateFillsArrayWithFunctionValues() {
        int numberOfRows = 3;
        int numberOfColumns = 2;

        A a = factory.generate(numberOfRows, numberOfColumns, (r, c) -> r * numberOfColumns + c);

        int value = 0;
        for (int r = 0; r < numberOfRows; ++r) {
            for (int c = 0; c < numberOfColumns; ++c) {
                assertThat((double) value++, equalTo(a.at(r, c)));
            }
        }
    }
}
