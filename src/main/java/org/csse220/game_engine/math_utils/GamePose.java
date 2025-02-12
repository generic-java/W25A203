package org.csse220.game_engine.math_utils;

import org.csse220.game_engine.graphics.Point3d;

public class GamePose extends Vector3d {
    private final double yaw;

    public GamePose(double x, double y, double z, double yaw) {
        super(x, y, z);
        this.yaw = yaw;
    }

    public GamePose() {
        this(0, 0, 0, 0);
    }

    public GamePose(Vector3d vector, double yaw) {
        this(vector.x(), vector.y(), vector.z(), yaw);
    }

    public double yaw() {
        return yaw;
    }

    public GamePose translate(Vector3d vector) {
        return translate(vector.x(), vector.y(), vector.z());
    }


    @Override
    public GamePose translate(double x, double y, double z) {
        Vector3d vector = super.translate(x, y, z);
        return new GamePose(vector.x(), vector.y(), vector.z(), yaw);
    }

    @Override
    public GamePose translateX(double x) {
        return translate(x, 0, 0);
    }

    @Override
    public GamePose translateY(double y) {
        return translate(0, y, 0);
    }

    @Override
    public GamePose translateZ(double z) {
        return translate(0, 0, z);
    }

    public GamePose relativeTo(GamePose pose) {
        return new GamePose(x() - pose.x(), y() - pose.y(), z() - pose.z(), yaw() - pose.yaw());
    }

    public GamePose normalize() {
        return scale(1 / magnitude());
    }

    public GamePose addTo(GamePose pose) {
        return new GamePose(x() + pose.x(), y() + pose.y(), z() + pose.z(), yaw() + pose.yaw());
    }

    public GamePose scale(double scalar) {
        return new GamePose(x() * scalar, y() * scalar, z() * scalar, yaw() * scalar);
    }

    @Override
    public GamePose rotateYaw(double yaw) {
        return new GamePose(super.rotateYaw(yaw), this.yaw);
    }

    public double dot(GamePose other) {
        return super.dot(other) + yaw() * other.yaw;
    }

    public GamePose round(int precision) {
        double factor = Math.pow(10, precision);
        return new GamePose(Math.round(x * factor) / factor, Math.round(y * factor) / factor, Math.round(z * factor) / factor, Math.round(yaw * factor) / factor);
    }

    public Point3d toPoint3d() {
        return new Point3d(x, y, z);
    }

    public boolean between(GamePose first, GamePose second) {
        double firstX;
        double firstY;

        double secondX;
        double secondY;

        if (first.x() < second.x()) {
            firstX = first.x();
            secondX = second.x();
        } else {
            firstX = second.x();
            secondX = first.x();
        }
        if (first.y() < second.y()) {
            firstY = first.y();
            secondY = second.y();
        } else {
            firstY = second.y();
            secondY = first.y();
        }

        return x >= firstX && x <= secondX && y >= firstY && y <= secondY;
    }
}
