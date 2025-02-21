package org.csse220.game_engine.kinematics;

/**
 * A utility class used by Hitbox.
 */
public class LineSegment {
    private final Point2d point1;
    private final Point2d point2;
    private final double lowestY;
    private final double highestY;
    private static final double MAX_ERROR = 1E-8;

    public LineSegment(Point2d point1, Point2d point2) {
        if (point2.x() > point1.x()) { // Arrange the points such point1 is always the leftmost
            this.point1 = point1;
            this.point2 = point2;
        } else {
            this.point1 = point2;
            this.point2 = point1;
        }
        if (point1.y() < point2.y()) {
            lowestY = point1.y();
            highestY = point2.y();
        } else {
            lowestY = point2.y();
            highestY = point1.y();
        }
    }

    public double lowestY() {
        return lowestY;
    }

    public double highestY() {
        return highestY;
    }

    public boolean intersectsWith(LineSegment other) {
        if (point1.equals(point2) || other.point1.equals(other.point2)) {
            return false;
        }
        double m = (point2.y() - point1.y()) / (point2.x() - point1.x());
        double k = (other.point2.y() - other.point1.y()) / (other.point2.x() - other.point1.x());
        double b = point1.y() - m * point1.x();
        double c = other.point1.y() - k * other.point1.x();
        if (!Double.isFinite(m) || !Double.isFinite(k)) { // ff any lines are vertical
            if (!Double.isFinite(m) && !Double.isFinite(k)) { // if both lines are vertical
                return point1.x() == other.point2.x() && highestY() > other.lowestY() && lowestY() < other.highestY();
            } else if (!Double.isFinite(m)) { // if line 1 is vertical
                if (k == 0) { // if line 1 is horizontal, we can't use the inverse because it would result in another vertical line
                    return point1.x() > other.point1.x() && point1.x() < other.point2.x() && highestY() > other.point1.y() && lowestY() < other.point1.y();
                } // if line 2 isn't horizontal, just invert both \/ \/
                return new LineSegment(new Point2d(point1.y(), point1.x()), new Point2d(point2.y(), point2.x())).intersectsWith(new LineSegment(new Point2d(other.point1.y(), other.point1.x()), new Point2d(other.point2.y(), other.point2.x())));
            } else { // if line 2 is vertical
                if (m == 0) { // if line 1 is horizontal, we can't use the inverse because it would result in another vertical line
                    return other.point1.x() > point1.x() && other.point1.x() < point2.x() && other.highestY() > point1.y() && other.lowestY() < point1.y();
                } // if line 1 isn't horizontal, just invert both \/ \/
                return new LineSegment(new Point2d(point1.y(), point1.x()), new Point2d(point2.y(), point2.x())).intersectsWith(new LineSegment(new Point2d(other.point1.y(), other.point1.x()), new Point2d(other.point2.y(), other.point2.x())));
            }
        } else if (Math.abs(m - k) < MAX_ERROR) { // Check if the two lines are actually the same line.  If they have the same slope
            return Math.abs(b - c) < MAX_ERROR;
        } else {
            double xIntersection = (c - b) / (m - k);
            return xIntersection >= point1.x() && xIntersection <= point2.x() && xIntersection >= other.point1.x() && xIntersection <= other.point2.x();
        }
    }

    @Override
    public String toString() {
        return "point1 - x: " + point1.x() + "\ny: " + point1.y() + "\npoint2 - x: " + point2.x() + "\ny: " + point2.y();
    }
}
