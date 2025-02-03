package org.csse220.game_engine.graphics;

public class Utils {
    public static double interpolate(double x1, double y1, double x2, double y2, double x) {
        if (x2 == x1) {
            throw new ArithmeticException("Error: division by zero in interpolation function");
        }
        return y1 + (x - x1) / (x2 - x1) * (y2 - y1);
    }

    /**
     * Rotates the point described by (x1, y1) abound (x2, y2)
     */
    public static double[] rotateAbout(double x1, double y1, double x2, double y2, double theta) {
        double newX = (x1 - x2) * Math.cos(theta) - (y1 - y2) * Math.sin(theta) + x2;
        double newY = (x1 - x2) * Math.sin(theta) + (y1 - y2) * Math.cos(theta) + y2;
        return new double[]{newX, newY};
    }
}
