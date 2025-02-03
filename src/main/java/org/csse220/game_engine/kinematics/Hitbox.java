package org.csse220.game_engine.kinematics;

import java.util.HashSet;
import java.util.Set;

public class Hitbox extends Collideable {
    private double x;
    private double y;
    private double z;
    private final double width;
    private final double height;
    private final double depth;
    private double rot;
    private final Point2d center;
    private final Point2d topLeft;
    private final Point2d topRight;
    private final Point2d bottomRight;
    private final Point2d bottomLeft;
    private final Set<Hitbox> hitboxes = new HashSet<>();

    public Hitbox(double x, double y, double z, double width, double height, double depth) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.width = width;
        this.height = height;
        this.depth = depth;
        center = new Point2d(x, y);
        topLeft = new Point2d(x - width / 2, y + depth / 2);
        topRight = new Point2d(x + width / 2, y + depth / 2);
        bottomRight = new Point2d(x + width / 2, y - depth / 2);
        bottomLeft = new Point2d(x - width / 2, y - depth / 2);
        hitboxes.add(this);
    }

    public Point2d xyCenter() {
        return new Point2d(x, y);
    }

    public double x() {
        return x;
    }

    public double y() {
        return y;
    }

    public double z() {
        return z;
    }

    public double rot() {
        return rot;
    }

    public double width() {
        return width;
    }

    public double height() {
        return height;
    }

    public double depth() {
        return depth;
    }

    void setX(double x) {
        this.x = x;
    }

    void setY(double y) {
        this.y = y;
    }

    void setZ(double z) {
        this.z = z;
    }

    private LineSegment[] getLineSegments() {
        return new LineSegment[]{
                new LineSegment(topLeft.rotateAbout(center, rot), topRight.rotateAbout(center, rot)),
                new LineSegment(topRight.rotateAbout(center, rot), bottomRight.rotateAbout(center, rot)),
                new LineSegment(bottomRight.rotateAbout(center, rot), bottomLeft.rotateAbout(center, rot)),
                new LineSegment(bottomLeft.rotateAbout(center, rot), topLeft.rotateAbout(center, rot))

        };
    }

    void setRot(double rot) {
        this.rot = rot;
    }

    @Override
    public Set<Hitbox> getHitboxes() {
        return hitboxes;
    }

    public boolean intersectsWith(Hitbox other) {
        boolean twoDimensionalIntersection = false;
        for (LineSegment segment : getLineSegments()) {
            for (LineSegment otherSegment : other.getLineSegments()) {
                if (segment.intersectsWith(otherSegment)) {
                    twoDimensionalIntersection = true;
                    break;
                }
            }
        }
        if (!twoDimensionalIntersection) {
            twoDimensionalIntersection = oneInside(other);
        }
        return twoDimensionalIntersection && zIntersection(other);
    }

    /**
     * @return Whether the projection of one hitbox onto the 2d plane is inside the projection of another
     */
    private boolean oneInside(Hitbox other, boolean checkOther) {
        Point2d otherCenter = other.xyCenter().rotateAbout(xyCenter(), -rot);
        return otherCenter.x() > x - width / 2 && otherCenter.x() < x + width / 2
                && otherCenter.y() > y - width / 2 && otherCenter.y() < y + width / 2
                && other.oneInside(this, false);
    }

    private boolean oneInside(Hitbox other) {
        return oneInside(other, true);
    }

    boolean zIntersection(Hitbox other) {
        return z + height / 2 > other.z() - other.height() / 2 && z - height / 2 < other.z() + other.height() / 2;
    }
}
