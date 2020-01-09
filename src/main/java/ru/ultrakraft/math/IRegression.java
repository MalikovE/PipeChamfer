package ru.ultrakraft.math;

import java.util.List;

/**
 * The {@code IRegression} interface provides methods for
 * approximation the specified list of points
 *
 * @author Malikov E.
 * @since 1.0
 */

public interface IRegression {
    /**
     * Returns the result of applying this regression
     * for the specified list of points
     *
     * @return the result of applying this regression
     * for the specified list of points
     */
    Object getResult();

    /**
     * Applies this regression for the specified list of points
     *
     * @param data - the specified list of points
     */
    void apply(final List<Point> data);
}
