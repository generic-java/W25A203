package org.csse220.game_engine.kinematics;

class LineSegment {
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
        double b = point1.y() - m * point1.x();
        double k = (other.point2.y() - other.point1.y()) / (other.point2.x() - other.point1.x());
        double c = other.point1.y() - k * other.point1.x();
        double xIntersection = (c - b) / (m - k);
        return xIntersection >= point1.x() && xIntersection <= point2.x()
                && xIntersection >= other.point1.x() && xIntersection <= other.point2.x();
    }
}
