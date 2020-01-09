package ru.ultrakraft.math;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the {@code IFilter} interface by simple
 * linear regression method.
 *
 * @author Malikov E.
 * @since 1.0
 */

public class RegressionFilter implements IFilter {
    /**
     * The threshold of the filter
     */
    private static final double THRESHOLD = 0.15;

    /**
     * Store the result of filtering of the specified list of points
     */
    private List<Point> result;

    /**
     * Returns the result of applying this filter
     * for the specified list of points
     *
     * @return the result of applying this filter
     * for the specified list of points
     */
    @Override
    public List<Point> getResult() {
        return result;
    }

    /**
     * Applies this filter for the specified list of points
     *
     * @param data - the specified list of points
     */
    @Override
    public void apply(List<Point> data) {
        result = new ArrayList<>();

        IRegression regression = new LinearRegression();
        regression.apply(data);

        Line l = (Line) regression.getResult();
        for (Point p : data) {
            if (p.getDistance(l) < THRESHOLD)
                result.add(p);
        }
    }
}
