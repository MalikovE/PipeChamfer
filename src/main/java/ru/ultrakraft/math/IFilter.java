package ru.ultrakraft.math;

import java.util.List;

/**
 * The {@code IFilter} interface provides methods for
 * filtering the specified list of points
 *
 * @author Malikov E.
 * @since 1.0
 */

public interface IFilter {
    /**
     * Returns the result of applying this filter
     * for the specified list of points
     *
     * @return the result of applying this filter
     * for the specified list of points
     */
    List<Point> getResult();

    /**
     * Applies this filter for the specified list of points
     *
     * @param data - the specified list of points
     */
    void apply(final List<Point> data);
}
