package ndarray;

import com.google.common.testing.EqualsTester;
import com.google.common.testing.NullPointerTester;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * Date: 01.01.16
 * Time: 13:50
 *
 * @author Evgeny Antaev
 */
@RunWith(Parameterized.class)
public class Array1dTest<A extends Array1d> {

    private final Array1dFactory<A> factory;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
            {NdArrays.immutable1d},
            {NdArrays.mutable1d}
        });
    }

    public Array1dTest(Array1dFactory<A> factory) {
        this.factory = factory;
    }

    @Test
    public void nonnullFactoryMethodsParamsAreChecked() {
        new NullPointerTester().testAllPublicInstanceMethods(factory);
    }

    @Test
    public void wrappedArrayNonnullMethodsParamsAreChecked() {
        new NullPointerTester().testAllPublicInstanceMethods(factory.wrap(1, 2, 3));
    }

    @Test
    public void copiedArrayNonnullMethodsParamsAreChecked() {
        new NullPointerTester().testAllPublicInstanceMethods(factory.copyOf(1, 2, 3));
    }

    @Test
    public void testEquals() {
        new EqualsTester()
            .addEqualityGroup(factory.copyOf(1, 2, 3), factory.wrap(1, 2, 3), factory.generateEagerly(3, i -> i + 1))
            .addEqualityGroup(factory.copyOf(1, 2), factory.wrap(1, 2), factory.generateEagerly(2, i -> i + 1))
        .testEquals();
    }

    @Test
    public void zerosCreatesArrayWithGivenLength() {
        int length = 3;

        A a = factory.zeros(length);

        assertThat(a.length(), equalTo(length));
    }

    @Test
    public void zerosCreatesArrayOfZeros() {
        int length = 10;

        A zeros = factory.zeros(length);

        for (int i = 0; i < 10; ++i) {
            assertThat(0d, equalTo(zeros.at(i)));
        }
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void accessingNegativeIndexThrowsIndexOutOfBounds() {
        factory.zeros(3).at(-1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void accessingIndexGteLengthThrowsIndexOutOfBounds() {
        factory.zeros(3).at(3);
    }

    @Test
    public void copyOfCreatesArrayOfSameLength() {
        double[] values = {1, 2, 3};

        A a = factory.copyOf(values);

        assertThat(a.length(), equalTo(values.length));
    }

    @Test
    public void copyOfCreatesArrayWithSameValues() {
        double[] values = {1, 2, 3};

        A a = factory.copyOf(values);

        for (int i = 0; i < values.length; ++i) {
            assertThat(a.at(i), equalTo(values[i]));
        }
    }

    @Test
    public void copyOfMakesCopyOfGivenArray() {
        double[] values = {1};

        A a = factory.copyOf(values);
        values[0] = 20;

        assertThat(a.at(0), equalTo(1d));
    }

    @Test
    public void wrapCreatesArrayOfSameLength() {
        double[] values = {1, 2, 3};

        A a = factory.wrap(values);

        assertThat(a.length(), equalTo(values.length));
    }

    @Test
    public void wrapCreatesArrayWithSameValues() {
        double[] values = {1, 2, 3};

        A a = factory.wrap(values);

        for (int i = 0; i < values.length; ++i) {
            assertThat(a.at(i), equalTo(values[i]));
        }
    }

    @Test
    public void wrapCreatesArrayBackedByGivenArray() {
        double[] values = {1};

        A a = factory.wrap(values);
        values[0] = 10;

        assertThat(a.at(0), equalTo(10d));
    }

    @Test
    public void generateCreatesArrayWithGivenLength() {
        int length = 5;

        A a = factory.generateEagerly(length, x -> x);

        assertThat(a.length(), equalTo(length));
    }

    @Test
    public void generateFillsArrayWithFunctionValues() {
        int length = 5;

        A a = factory.generateEagerly(length, x -> x * x);

        assertThat(a.at(3), equalTo(9d));
    }

    @Test
    public void stringRepresentationEquivalentToSimpleArray() {
        double[] values = {1, 2, 3};

        A array = factory.wrap(values);

        assertEquals(Arrays.toString(values), array.toString());
    }
}
