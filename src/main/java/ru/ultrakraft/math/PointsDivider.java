package ru.ultrakraft.math;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the instant for dividing the specified
 * list of points modelling a chain of 3 edges for a few lists of
 * points for each edge.
 */

public class PointsDivider {
    private final static double THRESHOLD = 0.2;

    /**
     * Store the result of dividing the specified list of points
     */
    private List<List<Point>> result;

    /**
     * Returns the result of dividing the specified list of points
     *
     * @return the result of dividing the specified list of points
     */
    public List<List<Point>> getResult() {
        return result;
    }

    /**
     * Divides the specified list of points modelling a chain of edges
     * for a few lists of points for each edge.
     *
     * @param scannerData - the specified list of points
     */
    public void calc(List<Point> scannerData) {
        List<Line> edges = new ArrayList<Line>();
        List<List<Point>> edgeGroups = new ArrayList<>();

        while (true) {
            double xAver = 0;
            for (Point p : scannerData)
                xAver += p.x();
            xAver /= scannerData.size();

            List<Point> group1 = new ArrayList<>();
            List<Point> group2 = new ArrayList<>();
            for (Point p : scannerData) {
                if (p.x() < xAver) {
                    group1.add(p);
                } else {
                    group2.add(p);
                }
            }

            LinearRegression regression = new LinearRegression();
            regression.apply(group1);
            Line line1 = (Line) regression.getResult();
            regression.apply(group2);
            Line line2 = (Line) regression.getResult();

            double dev1 = 0, dev2 = 0;
            for (Point p : group1)
                dev1 += p.getDistance(line1);

            for (Point p : group2)
                dev2 += p.getDistance(line2);

            Line edge; // = new Line(0, 0, 0);
            List<Point> edgeGroup = new ArrayList<>();
            if (dev1 < dev2) {
                edge = line1;
                edgeGroup = group1;
            } else {
                edge = line2;
                edgeGroup = group2;
            }
            edges.add(edge);
            edgeGroups.add(edgeGroup);
            if (edges.size() == 3) break;

            int pivotIndex = 0;
            for (int i = 0; i < scannerData.size(); i++) {
                if (scannerData.get(i).getDistance(edge) > THRESHOLD) {
                    pivotIndex = i;
                    break;
                }
            }
            scannerData = scannerData.subList(pivotIndex, scannerData.size() - 1);
        }
        result = edgeGroups;
    }
}
