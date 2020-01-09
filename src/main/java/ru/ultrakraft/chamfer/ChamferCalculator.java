package ru.ultrakraft.chamfer;

import ru.ultrakraft.math.*;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the instant for calculating parameters
 * of the chamfer for the specified scan.
 */

public class ChamferCalculator {
    /**
     * Store the result of calculating
     */
    private Chamfer result;

    /**
     * Returns the result of calculating
     *
     * @return the result of calculating
     */
    public Chamfer getResult() {
        return result;
    }

    /**
     * Calculates parameters of the chamfer for the specified scan
     *
     * @param scan - the specified scan
     * @param thickness - the specified thickness
     */
    public void calc(Scan scan, final double thickness) {
        List<Point> scannerData = scan.getData();
        PointsDivider pointsDivider = new PointsDivider();
        pointsDivider.calc(scannerData);
        List<List<Point>> edgeGroups = pointsDivider.getResult();

        IFilter filter = new RegressionFilter();
        List<List<Point>> filteredEdgeGroups = new ArrayList<>();
        for (List<Point> edgeGroup : edgeGroups) {
            filter.apply(edgeGroup);
            filteredEdgeGroups.add(filter.getResult());
        }

        List<Line> edges = new ArrayList<>();
        LinearRegression regression = new LinearRegression();
        for (List<Point> edgeGroup : filteredEdgeGroups) {
            regression.apply(edgeGroup);
            edges.add((Line) regression.getResult());
        }

        List<Point> vertices = new ArrayList<>();
        for (int i = 0; i < edges.size() - 1; i++) {
            Point vertex = edges.get(i).intersect(edges.get(i+1));
            vertices.add(vertex);
        }

        double angle = Math.PI/2 - edges.get(0).getAngle(edges.get(1));
        double width = vertices.get(0).getDistance(vertices.get(1));
        double depth = width * Math.cos(angle);
        double blunting = thickness - depth;

        result = new Chamfer(angle * (180 / Math.PI), thickness, blunting);
    }
}
