package ru.ultrakraft.math;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Implementation of the {@code IFilter} interface by
 * median filter. Provides methods for filtering the
 * specified list of points by median filter.
 *
 * @author Malikov E.
 * @since 1.0
 */

public class MedianFilter implements IFilter {
    /**
     * The radius of the smoothing window
     */
    private static final int RADIUS_MEDIAN = 1;

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
        for (int i = RADIUS_MEDIAN; i < data.size() - 1 - RADIUS_MEDIAN; i++) {
            List<Double> list = new ArrayList<>();
            for (int j = i - RADIUS_MEDIAN; j <= i + RADIUS_MEDIAN; j++)
                list.add(data.get(j).y());
            result.add(new Point(data.get(i).x(), median(list)));
        }
    }

    /**
     * Returns the median of the specified list of numbers
     *
     * @param args - the specified list of numbers
     * @return the median of the specified list of numbers
     */
    private double median(List<Double> args) {
        Collections.sort(args);
        if (args.size() % 2 != 0)
            return args.get(args.size() / 2);
        else
            return (args.get(args.size() / 2) + args.get(args.size() / 2 - 1)) / 2;
    }
}
