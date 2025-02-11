package org.csse220.game_engine.kinematics;

public class LineSegment {
    private final static double MAX_ERROR = 1E-8;
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
        double b = point1.y() - m * point1.x();
        double c = other.point1.y() - k * other.point1.x();
        if (Math.abs(m - k) < MAX_ERROR) { // Check if the two lines are actually the same line
            return Math.abs(b - c) < MAX_ERROR;
        }
        if (!Double.isFinite(m) || !Double.isFinite(k)) { // If any lines are vertical, flip them
            if (m == 0) { // If one line is vertical but another is horizontal, flipping the lines results in the same problem: 1 line becomes horizontal and the other becomes vertical
                return other.point1.x() > point1.x() && other.point1.x() < other.point2.x();
            } else if (k == 0) { // If this line segment is vertical
                return point1.x() > other.point1.x() && point1.x() < other.point2.x();
            } else {
                return point1.x() == point2.x();
            }
        }

        double xIntersection = (c - b) / (m - k);
        return xIntersection >= point1.x() && xIntersection <= point2.x()
                && xIntersection >= other.point1.x() && xIntersection <= other.point2.x();
    }
}
