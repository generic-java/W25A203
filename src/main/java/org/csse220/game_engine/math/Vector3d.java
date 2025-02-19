package org.csse220.game_engine.math;

import org.csse220.game_engine.graphics.Point3d;

public class Vector3d extends Vector2d {
    private static double cosPitch;
    private static double sinPitch;
    private static double cosYaw;
    private static double sinYaw;

    public static void updatePitchYaw(double camPitch, double camYaw) {
        cosPitch = Math.cos(-camPitch);
        sinPitch = Math.sin(-camPitch);
        cosYaw = Math.cos(-camYaw);
        sinYaw = Math.sin(-camYaw);
    }

    public final double z;
    private double magnitude = Double.NaN;

    /**
     * A unit vector in the direction of the positive x-axis.
     */
    public static final Vector3d X_AXIS = new Vector3d(1, 0, 0);
    /**
     * A unit vector in the direction of the positive y-axis.
     */
    public static final Vector3d Y_AXIS = new Vector3d(0, 1, 0);
    /**
     * A unit vector in the direction of the positive z-axis.
     */
    public static final Vector3d Z_AXIS = new Vector3d(0, 0, 1);

    public static final Vector3d ORIGIN = new Vector3d();

    public Vector3d(double x, double y, double z) {
        super(x, y);
        this.z = z;
    }

    public Vector3d() {
        this(0, 0, 0);
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

    /**
     * Rotates in the yaw direction first, and then the pitch direction
     *
     * @param vector The Vector3d to rotate
     * @return The new, rotated Vector3d
     */
    public Vector3d rotatePitchYaw(Vector3d vector) {
        double relX = x - vector.x();
        double relY = y - vector.y();
        double relZ = z - vector.z();
        double finalX = relX * cosYaw - relY * sinYaw;
        double tempY = relX * sinYaw + relY * cosYaw;
        double finalY = tempY * cosPitch - relZ * sinPitch;
        double finalZ = tempY * sinPitch + relZ * cosPitch;
        return new Vector3d(finalX + vector.x(), finalY + vector.y(), finalZ + vector.z());
    }

    public Vector3d rotatePitchYaw(double pitch, double yaw) {
        return rotatePitchYaw(ORIGIN);
    }

    public Vector3d rotateYaw(Vector3d center, double yaw) {
        Vector2d rotatedVector = new Vector2d(x, y).translate(-center.x(), -center.y()).rotate(yaw).translate(center.x(), center.y());
        return new Vector3d(rotatedVector.x, rotatedVector.y, z);
    }

    public Vector3d rotatePitch(Vector3d center, double pitch) {
        Vector2d rotatedVector = new Vector2d(y, z).translate(-center.x(), -center.y()).rotate(pitch).translate(center.x(), center.y());
        return new Vector3d(x, rotatedVector.x, rotatedVector.y);
    }

    public Vector3d rotateYaw(double yaw) {
        return rotateYaw(ORIGIN, yaw);
    }

    public Vector3d round(int precision) {
        double factor = Math.pow(10, precision);
        return new Vector3d(Math.round(x * factor) / factor, Math.round(y * factor) / factor, Math.round(z * factor) / factor);
    }

    public double distanceTo(Vector3d point) {
        return Math.sqrt(Math.pow(x - point.x(), 2) + Math.pow(y - point.y(), 2) + Math.pow(z - point.z(), 2));
    }

    public boolean equals(Object o) {
        if (o instanceof Vector3d vector) {
            return x == vector.x() && y == vector.y() && z == vector.z();
        }
        return false;
    }

    public double dot(Vector3d vector) {
        return x * vector.x() + y * vector.y() + z * vector.z();
    }

    public Vector3d cross(Vector3d vector) {
        return new Vector3d(
                vector.z() * y - vector.y() * z,
                vector.x() * z - vector.z() * x,
                vector.y() * x - vector.x() * y
        );
    }

    public Vector3d relativeTo(Vector3d vector) {
        return new Vector3d(x - vector.x(), y - vector.y(), z - vector.z());
    }

    public double magnitude() {
        if (Double.isNaN(magnitude)) {
            magnitude = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2));
        }
        return magnitude;
    }

    public double angleBetween(Vector3d vector) {
        return Math.acos(dot(vector) / (magnitude() * vector.magnitude()));
    }

    public Vector3d translate(Vector3d translation) {
        return translate(translation.x(), translation.y(), translation.z());
    }

    public Vector3d addTo(Vector3d vector) {
        return new Vector3d(x + vector.x(), y + vector.y(), vector.z());
    }

    public Vector3d translate(double x, double y, double z) {
        return new Vector3d(this.x + x, this.y + y, this.z + z);
    }

    public Vector3d translateX(double x) {
        return translate(x, 0, 0);
    }

    public Vector3d translateY(double y) {
        return translate(0, y, 0);
    }

    public Vector3d translateZ(double z) {
        return translate(0, 0, z);
    }

    public Vector3d scale(double scalar) {
        return new Vector3d(x * scalar, y * scalar, z * scalar);
    }

    public Point3d toPoint3d() {
        return new Point3d(this);
    }

    public GamePose toGamePose() {
        return new GamePose(x, y, z, 0);
    }

    @Override
    public String toString() {
        return String.format("x: %s\ny: %s\nz: %s", x, y, z);
    }
}
