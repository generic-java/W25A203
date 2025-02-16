package org.csse220.game_engine.kinematics;

import org.csse220.game_engine.math_utils.GamePose;

import java.util.HashSet;
import java.util.Set;

public class Hitbox extends Collideable {
    private final double width;
    private final double height;
    private final double depth;
    private Point2d center;
    private Point2d topLeft;
    private Point2d topRight;
    private Point2d bottomRight;
    private Point2d bottomLeft;
    private final Set<Hitbox> thisHitbox = new HashSet<>();

    public Hitbox(GamePose pose, double width, double height, double depth) {
        super(pose);
        this.width = width;
        this.height = height;
        this.depth = depth;
        updateVertexPositions();
        thisHitbox.add(this);
    }

    public Point2d xyCenter() {
        return new Point2d(pose.x(), pose.y());
    }

    private void updateVertexPositions() {
        center = xyCenter();
        topLeft = new Point2d(pose.x() - width / 2, pose.y() + depth / 2);
        topRight = new Point2d(pose.x() + width / 2, pose.y() + depth / 2);
        bottomRight = new Point2d(pose.x() + width / 2, pose.y() - depth / 2);
        bottomLeft = new Point2d(pose.x() - width / 2, pose.y() - depth / 2);
    }

    @Override
    public void setPose(GamePose pose) {
        super.setPose(pose);
        updateVertexPositions();
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


    private LineSegment[] getLineSegments() {
        return new LineSegment[]{
                new LineSegment(topLeft.rotateAbout(center, pose.yaw()), topRight.rotateAbout(center, pose.yaw())),
                new LineSegment(topRight.rotateAbout(center, pose.yaw()), bottomRight.rotateAbout(center, pose.yaw())),
                new LineSegment(bottomRight.rotateAbout(center, pose.yaw()), bottomLeft.rotateAbout(center, pose.yaw())),
                new LineSegment(bottomLeft.rotateAbout(center, pose.yaw()), topLeft.rotateAbout(center, pose.yaw()))

        };
    }

    public boolean intersects(Hitbox other) {
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
        Point2d otherCenter = other.xyCenter().rotateAbout(xyCenter(), -pose.yaw());
        boolean isInside = false;
        if (checkOther) {
            isInside = other.oneInside(this, false);
        }
        return otherCenter.x() > pose.x() - width / 2 && otherCenter.x() < pose.x() + width / 2 && otherCenter.y() > pose.y() - depth / 2 && otherCenter.y() < pose.y() + depth / 2 || isInside;
    }

    private boolean oneInside(Hitbox other) {
        return oneInside(other, true);
    }

    boolean zIntersection(Hitbox other) {
        return pose.z() + height / 2 >= other.getPose().z() - other.height() / 2 && pose.z() - height / 2 <= other.getPose().z() + other.height() / 2;
    }

    @Override
    public Set<Hitbox> getHitboxes() {
        return thisHitbox;
    }
}
