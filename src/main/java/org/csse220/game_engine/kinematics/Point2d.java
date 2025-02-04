package org.csse220.game_engine.kinematics;

public record Point2d(double x, double y) {

    public Point2d rotateAbout(Point2d center, double theta) {
        double newX = (x - center.x()) * Math.cos(theta) - (y - center.y()) * Math.sin(theta) + center.x();
        double newY = (x - center.x()) * Math.sin(theta) + (y - center.y()) * Math.cos(theta) + center.y();
        return new Point2d(newX, newY);
    }
}
