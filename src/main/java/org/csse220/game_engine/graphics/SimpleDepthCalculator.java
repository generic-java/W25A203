package org.csse220.game_engine.graphics;

/**
 * Uses basic depth interpolation math to find the depth of a pixel that has been projected onto the screen.
 */
public class SimpleDepthCalculator implements DepthCalculator {
    private final double c1;
    private final double c2;
    private final double c3;
    private final double c4;

    public SimpleDepthCalculator(double c1, double c2, double c3, double c4) {
        this.c1 = c1;
        this.c2 = c2;
        this.c3 = c3;
        this.c4 = c4;
    }

    @Override
    public double calculateDepth(double projectedX, double projectedY) {
        double depth = c1 * Point3d.projectionConstant() / (Point3d.projectionConstant() * c4 - c2 * projectedY - c3 * projectedX);
        return depth < 0.1 ? Double.POSITIVE_INFINITY : depth;
    }
}
