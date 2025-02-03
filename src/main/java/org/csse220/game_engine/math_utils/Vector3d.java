package org.csse220.game_engine.math_utils;

public class Vector3d {
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

    private final double x;
    private final double y;
    private final double z;
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
        this.x = x;
        this.y = y;
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

    public Vector3d rotatePitchYaw(Vector3d point) {
        double relX = x - point.x();
        double relY = y - point.y();
        double relZ = z - point.z();
        double finalX = relX * cosYaw - relY * sinYaw;
        double tempY = relX * sinYaw + relY * cosYaw;
        double finalY = tempY * cosPitch - relZ * sinPitch;
        double finalZ = tempY * sinPitch + relZ * cosPitch;
        return new Vector3d(finalX + point.x(), finalY + point.y(), finalZ + point.z());
    }

    public Vector3d rotatePitchYaw(double pitch, double yaw) {
        return rotatePitchYaw(ORIGIN);
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

    public Vector3d translate(double x, double y, double z) {
        return new Vector3d(this.x + x, this.y + y, this.z + z);
    }

    @Override
    public String toString() {
        return String.format("x: %s\ny: %s\nz: %s", x, y, z);
    }
}
