package ndarray;

import javax.annotation.Nonnull;

/**
 * Date: 16.01.16
 * Time: 13:04
 *
 * @author Evgeny Antaev
 */
public interface ArrayFactory {

    /**
     * Creates an immutable array with all elements equal to 0
     *
     * @param shape shape of the resulting array
     * @return immutable array of zeros
     */
    Array constantZero(@Nonnull Shape shape);

    /**
     * Creates a new array with given shape. All elements are initialized to 0
     *
     * @param shape shape of the resulting array
     * @return new array of the given shape with all elements initialized to 0
     */
    Array create(@Nonnull Shape shape);

    /**
     * Creates a new array with given shape and copies data elements into it.
     *
     * @param shape shape of the resulting array
     * @param data  data to initialize array. Length of data should be equal to shape's {@link Shape#numberOfElements()}
     *              Otherwise {@link IllegalArgumentException} is thrown.
     * @return new array of the given shape with elements initialized by provided data array
     */
    Array copyOf(@Nonnull Shape shape, @Nonnull double... data);

    /**
     * Creates a copy of given array
     *
     * @param array array to copy
     * @return a copy of provided array
     */
    Array copyOf(@Nonnull Array array);

    /**
     * Creates a new array with given shape backed by provided data array.
     *
     * @param shape shape of the resulting array
     * @param data  backing array. Length of data should be equal to shape's {@link Shape#numberOfElements()}
     *              Otherwise {@link IllegalArgumentException} is thrown.
     * @return new array of the given shape with data used as backing storage
     */
    Array wrap(@Nonnull Shape shape, @Nonnull double... data);
}
