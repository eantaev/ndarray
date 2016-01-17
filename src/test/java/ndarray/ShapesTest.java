package ndarray;

import org.junit.Test;

import java.util.Iterator;

import static ndarray.Positions.position;
import static ndarray.Shapes.shape;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Date: 16.01.16
 * Time: 14:04
 *
 * @author Evgeny Antaev
 */
public class ShapesTest {

    @Test(expected = IllegalArgumentException.class)
    public void negativeDimensionsAreForbidden() {
        shape(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void zeroLengthShapeIsForbidden() {
        shape();
    }

    @Test
    public void singleDimension() {
        assertThat(shape(10).numberOfAxes(), equalTo(1));
    }

    @Test
    public void twoDimensions() {
        assertThat(shape(10, 5).numberOfAxes(), equalTo(2));
    }

    @Test(expected = IllegalArgumentException.class)
    public void elementIndexFailsWhenPositionHasDifferentShape() {
        shape(4, 3, 7).elementIndex(position(2, 3));
    }

    @Test
    public void elementIndexIn1dShapeEqSingleCoordinate() {
        assertThat(shape(10).elementIndex(position(5)), equalTo(5));
    }

    @Test
    public void elementIndexIn2dShape() {
        assertThat(shape(10, 5).elementIndex(position(2, 3)), equalTo(3 + 2 * 5));
    }

    @Test
    public void elementIndexIn3dShape() {
        assertThat(shape(10, 5, 7).elementIndex(position(2, 3, 4)), equalTo(4 + 3 * 7 + 2 * 7 * 5));
    }

    @Test
    public void iterateSingleDimensionShape() {
        int length = 5;

        Iterator<Position> it = shape(length).iterator();

        for (int i = 0; i < length; ++i) {
            assertTrue(it.hasNext());
            assertThat(it.next(), equalTo(position(i)));
        }
    }

    @Test
    public void iterateTwoDimensionalShape() {
        int numberOfRows = 5;
        int numberOfCols = 3;

        Iterator<Position> it = shape(numberOfRows, numberOfCols).iterator();

        for (int row = 0; row < numberOfRows; ++row) {
            for (int col = 0; col < numberOfCols; ++col) {
                assertTrue(it.hasNext());
                assertThat(it.next(), equalTo(position(row, col)));
            }
        }
    }

    @Test
    public void sliceHas1LessNumberOfAxes() {
        Shape orig = shape(5, 3, 7);

        Shape slice = orig.slice(1);

        assertThat(slice.numberOfAxes(), equalTo(orig.numberOfAxes() - 1));
    }

    @Test(expected = IllegalStateException.class)
    public void slicingSingleDimensionalShapeCausesException() {
        shape(5).slice(0);
    }

    @Test
    public void sliceNumberOfElements() {
        Shape orig = shape(5, 3, 7);

        assertThat(orig.slice(0).numberOfElements(), equalTo(orig.axisLength(1) * orig.axisLength(2)));
        assertThat(orig.slice(1).numberOfElements(), equalTo(orig.axisLength(0) * orig.axisLength(2)));
        assertThat(orig.slice(2).numberOfElements(), equalTo(orig.axisLength(0) * orig.axisLength(1)));
    }

    @Test
    public void sliceAxis0Iteration() {
        int numberOfItems = 7;
        int numberOfRows = 5;
        int numberOfCols = 3;

        Iterator<Position> it = shape(numberOfItems, numberOfRows, numberOfCols)
            .slice(0).iterator();

        for (int row = 0; row < numberOfRows; ++row) {
            for (int col = 0; col < numberOfCols; ++col) {
                assertTrue(it.hasNext());
                assertThat(it.next(), equalTo(position(row, col)));
            }
        }
    }

    @Test
    public void sliceAxis1Iteration() {
        int numberOfItems = 7;
        int numberOfRows = 5;
        int numberOfCols = 3;

        Iterator<Position> it = shape(numberOfItems, numberOfRows, numberOfCols)
            .slice(1).iterator();

        for (int item = 0; item < numberOfRows; ++item) {
            for (int col = 0; col < numberOfCols; ++col) {
                assertTrue(it.hasNext());
                assertThat(it.next(), equalTo(position(item, col)));
            }
        }
    }

    @Test
    public void sliceAxis2Iteration() {
        int numberOfItems = 7;
        int numberOfRows = 5;
        int numberOfCols = 3;

        Iterator<Position> it = shape(numberOfItems, numberOfRows, numberOfCols)
            .slice(2).iterator();

        for (int item = 0; item < numberOfItems; ++item) {
            for (int row = 0; row < numberOfRows; ++row) {
                assertTrue(it.hasNext());
                assertThat(it.next(), equalTo(position(item, row)));
            }
        }
    }

    @Test
    public void insertAxis0Iteration() {
        int numberOfItems = 7;
        int numberOfRows = 5;
        int numberOfCols = 3;

        Iterator<Position> it = shape(numberOfRows, numberOfCols)
            .insert(0, numberOfItems).iterator();

        for (int item = 0; item < numberOfItems; ++item) {
            for (int row = 0; row < numberOfRows; ++row) {
                for (int col = 0; col < numberOfCols; ++col) {
                    assertTrue(it.hasNext());
                    assertThat(it.next(), equalTo(position(item, row, col)));
                }
            }
        }
    }

    @Test
    public void insertAxis1Iteration() {
        int numberOfItems = 7;
        int numberOfRows = 5;
        int numberOfCols = 3;

        Iterator<Position> it = shape(numberOfItems, numberOfCols)
            .insert(1, numberOfRows).iterator();

        for (int item = 0; item < numberOfItems; ++item) {
            for (int row = 0; row < numberOfRows; ++row) {
                for (int col = 0; col < numberOfCols; ++col) {
                    assertTrue(it.hasNext());
                    assertThat(it.next(), equalTo(position(item, row, col)));
                }
            }
        }
    }

    @Test
    public void insertAxis2Iteration() {
        int numberOfItems = 7;
        int numberOfRows = 5;
        int numberOfCols = 3;

        Iterator<Position> it = shape(numberOfItems, numberOfRows)
            .insert(2, 3).iterator();

        for (int item = 0; item < numberOfItems; ++item) {
            for (int row = 0; row < numberOfRows; ++row) {
                for (int col = 0; col < numberOfCols; ++col) {
                    assertTrue(it.hasNext());
                    assertThat(it.next(), equalTo(position(item, row, col)));
                }
            }
        }
    }
}