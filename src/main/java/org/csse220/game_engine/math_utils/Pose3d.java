package org.csse220.game_engine.math_utils;

public class Pose3d extends Vector3d {
    private final double pitch;
    private final double roll;
    private final double yaw;

    public Pose3d(double x, double y, double z, double pitch, double roll, double yaw) {
        super(x, y, z);
        this.pitch = pitch;
        this.roll = roll;
        this.yaw = yaw;
    }

    public Pose3d() {
        this(0, 0, 0, 0, 0, 0);
    }

    public double pitch() {
        return pitch;
    }

    public double roll() {
        return roll;
    }

    public double yaw() {
        return yaw;
    }

    public Pose3d translate(Vector3d vector) {
        return translate(vector.x(), vector.y(), vector.z());
    }

    @Override
    public Pose3d translate(double x, double y, double z) {
        Vector3d vector = super.translate(x, y, z);
        return new Pose3d(vector.x(), vector.y(), vector.z(), pitch, roll, yaw);
    }

    @Override
    public Pose3d translateX(double x) {
        return translate(x, 0, 0);
    }

    @Override
    public Pose3d translateY(double y) {
        return translate(0, y, 0);
    }

    @Override
    public Pose3d translateZ(double z) {
        return translate(0, 0, z);
    }
}
