package ndarray;

import org.junit.Test;

import static ndarray.Positions.position;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Date: 16.01.16
 * Time: 14:24
 *
 * @author Evgeny Antaev
 */
public class PositionTest {
    @Test(expected = IllegalArgumentException.class)
    public void negativeCoordinatesAreForbidden() {
        position(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void zeroLengthCoordinatesAreForbidden() {
        position();
    }

    @Test
    public void singleDimension() {
        assertThat(position(5).numberOfAxes(), equalTo(1));
    }

    @Test
    public void twoDimensions() {
        assertThat(position(5, 3).numberOfAxes(), equalTo(2));
    }
}
