package org.csse220.game_engine.kinematics;

public class Point2d {
    private final double x;
    private final double y;

    public Point2d(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double x() {
        return x;
    }

    public double y() {
        return y;
    }

    public Point2d rotateAbout(Point2d center, double theta) {
        double newX = (x - center.x()) * Math.cos(theta) - (y - center.y()) * Math.sin(theta) + center.x();
        double newY = (x - center.x()) * Math.sin(theta) + (y - center.y()) * Math.cos(theta) + center.y();
        return new Point2d(newX, newY);
    }
}
