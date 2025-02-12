package org.csse220.game_engine.kinematics;

public class LineSegment {
    private final Point2d point1;
    private final Point2d point2;

    public LineSegment(Point2d point1, Point2d point2) {
        if (point2.x() > point1.x()) { // Arrange the points such point1 is always the leftmost
            this.point1 = point1;
            this.point2 = point2;
        } else {
            this.point1 = point2;
            this.point2 = point1;
        }
    }

    public boolean intersectsWith(LineSegment other) {
        double m = (point2.y() - point1.y()) / (point2.x() - point1.x());
        double k = (other.point2.y() - other.point1.y()) / (other.point2.x() - other.point1.x());
        if (Math.abs(m - k) == 0) { // Check if the two lines are actually the same line
            return true;
        } else if (!Double.isFinite(m) || !Double.isFinite(k)) { // ff any lines are vertical
            if (!Double.isFinite(m) && !Double.isFinite(k)) { // if both lines are vertical
                return point1.x() == other.point2.x();
            } else if (!Double.isFinite(m)) { // if m is vertical
                return point1.x() >= other.point1.x() && point1.x() <= other.point2.x();
            } else { // if k is vertical
                return other.point1.x() >= point1.x() && other.point1.x() <= point2.x();
            }
        } else {
            double b = point1.y() - m * point1.x();
            double c = other.point1.y() - k * other.point1.x();
            double xIntersection = (c - b) / (m - k);
            return xIntersection >= point1.x() && xIntersection <= point2.x() && xIntersection >= other.point1.x() && xIntersection <= other.point2.x();
        }
    }
}
