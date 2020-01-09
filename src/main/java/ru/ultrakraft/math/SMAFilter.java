package ru.ultrakraft.math;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the {@code IFilter} interface by simple
 * moving average method. Provides methods for filtering the
 * specified list of points by simple moving average method.
 *
 * @author Malikov E.
 * @since 1.0
 */

public class SMAFilter implements IFilter {
    /**
     * The radius of the smoothing window
     */
    private static final int RADIUS_SMA = 1;

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
        for (int i = RADIUS_SMA; i < data.size() - 1 - RADIUS_SMA; i++) {
            double sumY = 0;
            for (int j = i - RADIUS_SMA; j <= i + RADIUS_SMA; j++)
                sumY += data.get(j).y();

            result.add(new Point(data.get(i).x(), sumY / (2 * RADIUS_SMA + 1)));
        }
    }
}
