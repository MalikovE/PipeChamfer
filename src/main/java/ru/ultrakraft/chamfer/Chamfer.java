package ru.ultrakraft.chamfer;

/**
 * This class represents a pipe chamfer
 *
 * @author Malikov E.
 * @since 1.0
 */

public class Chamfer {
    /**
     * Parameters of this chamfer
     */
    private double angle;
    private double width;
    private double depth;
    private double blunting;
    private double thickness;

    /**
     * Constructs a {@code Chamfer} object and initializes it
     * by the {@code angle}, {@code thickness} and {@code blunting} arguments
     *
     * @param angle - the specified angle in degrees
     * @param thickness - the specified thickness
     * @param blunting - the specified blunting
     */
    public Chamfer(double angle, double thickness, double blunting) {
        this.angle = angle;
        this.thickness = thickness;
        this.blunting = blunting;
        this.depth = this.thickness - this.blunting;
        this.width = this.depth / Math.cos(this.angle * (Math.PI / 180));
    }

    /**
     * Returns a value of {@link Chamfer#angle}
     *
     * @return a value of {@link Chamfer#angle}
     */
    public double getAngle() {
        return angle;
    }

    /**
     * Returns a value of {@link Chamfer#width}
     *
     * @return a value of {@link Chamfer#width}
     */
    public double getWidth() {
        return width;
    }

    /**
     * Returns a value of {@link Chamfer#depth}
     *
     * @return a value of {@link Chamfer#depth}
     */
    public double getDepth() {
        return depth;
    }

    /**
     * Returns a value of {@link Chamfer#blunting}
     *
     * @return a value of {@link Chamfer#blunting}
     */
    public double getBlunting() {
        return blunting;
    }

    /**
     * Returns a value of {@link Chamfer#thickness}
     *
     * @return a value of {@link Chamfer#thickness}
     */
    public double getThickness() { return thickness; }

    /**
     * Return a string representation of this chamfer
     *
     * @return  a string representation of this chamfer
     */
    @Override
    public String toString() {
        return String.format("\nAngle = %.2f\nWidth = %.2f\n" +
                        "Depth = %.2f\nBlunting = %.2f\nThickness = %.2f\n",
                angle, width, depth, blunting, thickness);
    }
}
