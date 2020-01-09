package ru.ultrakraft.math;

/**
 * This class represents a line on a plane
 *
 * @author Malikov E.
 * @since 1.0
 */

public class Line {
    /**
     * Coefficients of the general equation of this line:
     * ax + by + c = 0
     */
    private double a, b, c;

    /**
     * Constructs a {@code Line} object and initializes it
     * by the {@code a}, {@code b} and {@code c} arguments
     *
     * @param a - the specified coefficient at x
     * @param b - the specified coefficient at y
     * @param c - the specified free member
     * @see Line#Line(double, double)
     */
    public Line(final double a, final double b, final double c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    /**
     * Constructs a {@code Line} object
     * by specified slope and intercept
     *
     * @param slope - the specified slope
     * @param intercept - the specified intercept
     * @see Line#Line(double, double, double)
     */
    public Line(final double slope, final double intercept) {
        this(slope, -1, intercept);
    }

    /**
     * Returns a value of {@link Line#a}
     *
     * @return a value of {@link Line#a}
     */
    public double a() {
        return a;
    }

    /**
     * Returns a value of {@link Line#b}
     *
     * @return a value of {@link Line#b}
     */
    public double b() {
        return b;
    }

    /**
     * Returns a value of {@link Line#c}
     *
     * @return a value of {@link Line#c}
     */
    public double c() {
        return c;
    }

    /**
     * Returns the intersection point between this and the specified lines
     *
     * @param l - the specified point
     * @return the intersection point between this and the specified lines
     */
    public Point intersect(Line l) {
        final double det = a * l.b() - b * l.a();
        if (det == 0) return null;
        final double detX = -c * l.b() + b * l.c();
        final double x = detX / det;
        final double y = (b != 0) ? -(a * x + c) / b : -(l.a() * x + l.c()) / l.b();
        return new Point(x, y);
    }

    /**
     * Returns the angle between this and the specified lines
     *
     * @param l - the specified point
     * @return the angle between this and the specified lines
     */
    public double getAngle(Line l) {
        return Math.acos((a * l.a() + b * l.b()) / (Math.sqrt(a * a
                + b * b) * Math.sqrt(l.a() * l.a() + l.b() * l.b())));
    }

    /**
     * Returns the distance between this line and the specified point
     *
     * @param p - the specified point
     * @return the distance between this line and the specified point
     */
    public double getDistanceToPoint(Point p) {
        return Math.abs(a * p.x() + b  * p.y() + c) /
                Math.sqrt(a * a + b * b);
    }
}
