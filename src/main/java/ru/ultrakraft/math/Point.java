package ru.ultrakraft.math;

import java.util.Objects;

/**
 * This class represents a point on a plane
 *
 * @author Malikov E.
 * @since 1.0
 */

public class Point {
    /**
     * Cartesian coordinates of this point
     */
    private double x, y;

    /**
     * Constructs a {@code Point} object and initializes it
     * by the {@code x}, {@code y} arguments
     *
     * @param x - the specified x
     * @param y - the specified y
     * @see Point#Point()
     */
    public Point(final double x, final double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Constructs a {@code Point} object and initializes
     * both arguments by zero
     *
     * @see Point#Point(double, double)
     */
    public Point() {
        this(0, 0);
    }

    /**
     * Returns a value of {@link Point#x}
     *
     * @return a value of {@link Point#x}
     */
    public double x() {
        return x;
    }

    /**
     * Returns a value of {@link Point#y}
     *
     * @return a value of {@link Point#y}
     */
    public double y() {
        return y;
    }

    /**
     * Returns the distance between this and specified points
     *
     * @param p - the specified point
     * @return a distance between this and specified points
     */
    public double getDistance(final Point p) {
        return Math.sqrt((x - p.x) * (x - p.x) + (y - p.y) * (y - p.y));
    }

    /**
     * Returns the distance between this point and the specified line
     *
     * @param l - the specified line
     * @return the distance between this point and the specified line
     */
    public double getDistance(final Line l) {
        return Math.abs(l.a() * x + l.b()  *y + l.c()) /
                Math.sqrt(l.a() * l.a() + l.b() * l.b());
    }

    /**
     * Compares the specified object with this point for equality.
     * Returns {@code true} if and only if the difference between
     * corresponding fields of this point is less specified precision EPS
     *
     * @param o the object to be compared for equality with this point
     * @return {@code true} if the specified object is equal to this point
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final double EPS = 1e-9;
        Point point = (Point) o;
        return Math.abs(point.x - x) < EPS &&
                Math.abs(point.y - y) < EPS;
    }

    /**
     * Returns the hash code value for this point. The hash code of a point
     * is defined to be the result of the following calculation:
     * <pre>{@code
     *     int hashCode = 1;
     *     for (E e : list)
     *         hashCode = 31*hashCode + (e==null ? 0 : e.hashCode());
     * }</pre>
     *
     * @return the hash code value for this point
     * @see Point#equals(Object)
     * @see #equals(Object)
     */
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    /**
     * Return a string representation of this point
     *
     * @return  a string representation of this point
     */
    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
