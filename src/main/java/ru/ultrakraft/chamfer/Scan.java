package ru.ultrakraft.chamfer;

import ru.ultrakraft.math.Point;

import java.util.List;

/**
 * This class represents a scan
 *
 * @author Malikov E.
 * @since 1.0
 */

public class Scan {
    /**
     * The list of points of this scan
     */
    private List<Point> scannerData;

    /**
     * Constructs a {@code Scan} object and initializes it
     * by the {@code scannerData} argument
     *
     * @param scannerData - the specified list of points
     */
    public Scan(final List<Point> scannerData) {
        this.scannerData = scannerData;
    }

    /**
     * Returns a reference of {@link Scan#scannerData}
     *
     * @return a reference of {@link Scan#scannerData}}
     */
    public List<Point> getData() {
        return this.scannerData;
    }

    /**
     * Returns the size of this scannerData
     *
     * @return the size of this scannerData
     */
    public int size() { return this.scannerData.size(); }

    /**
     * Return a string representation of this scan
     *
     * @return  a string representation of this scan
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (Point p : scannerData) {
            result.append(p.toString()).append("\n");
        }
        return result.toString();
    }
}
