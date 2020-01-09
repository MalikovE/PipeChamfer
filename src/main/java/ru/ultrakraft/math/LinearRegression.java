package ru.ultrakraft.math;

import java.util.List;

/**
 * Implementation of the {@code IRegression} interface. Provides
 * methods for approximation the specified list of points by line.
 *
 * @author Malikov E.
 * @since 1.0
 */

public class LinearRegression implements IRegression {
    /**
     * Store the result of applying this regression
     */
    private Line result;

    /**
     * Returns the result of applying this regression
     * for the specified list of points
     *
     * @return the result of applying this regression
     * for the specified list of points
     */
    @Override
    public Object getResult() {
        return result;
    }

    /**
     * Applies this regression for the specified list of points
     *
     * @param data - the specified list of points
     */
    @Override
    public void apply(List<Point> data) {
        double sumX = 0, sumY = 0, sumXX = 0, sumXY = 0;
        int n = data.size();

        for (Point p : data) {
            sumX += p.x();
            sumY += p.y();
            sumXX += p.x() * p.x();
            sumXY += p.x() * p.y();
        }

        double slope = (n * sumXY - sumX * sumY) / (n * sumXX - sumX * sumX);
        result = new Line(slope, (sumY - slope * sumX) / n);
    }
}
