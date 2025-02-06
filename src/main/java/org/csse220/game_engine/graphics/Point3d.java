package org.csse220.game_engine.graphics;

import org.csse220.game_engine.math_utils.Vector3d;

public class Point3d extends Vector3d {
    private static double FOV;
    private static double FOV_CONSTANT;

    static {
        setFOV(Math.toRadians(140));
    }

    public static void setFOV(double FOV) {
        Point3d.FOV = FOV;
        Point3d.FOV_CONSTANT = 2 * Math.tan(FOV / 2); // The multiple of 2 isn't really associated with the FOV - it's just a constant factor that divides the screen width by 2 in the long run
    }

    public static double getFOV() {
        return FOV;
    }

    private Vector3d currentRelativePos;

    public Point3d(double x, double y, double z) {
        super(x, y, z);
        currentRelativePos = this;
    }

    public double relativeX() {
        return currentRelativePos.x();
    }

    public double relativeY() {
        return currentRelativePos.y();
    }

    public double relativeZ() {
        return currentRelativePos.z();
    }

    public void calculateRelativePosition(Vector3d camPos) {
        Vector3d rotatedPos = rotatePitchYaw(Camera.getInstance().getPosition());
        currentRelativePos = new Vector3d(rotatedPos.x() - camPos.x(), rotatedPos.y() - camPos.y(), rotatedPos.z() - camPos.z());
    }

    public ProjectedPoint project() {
        return project(relativeX(), relativeY(), relativeZ());
    }

    public static ProjectedPoint project(double relX, double relY, double relZ) {
        return new ProjectedPoint(
                projectionConstant() * relX / relY,
                projectionConstant() * relZ / relY
        );
    }

    public static double projectionConstant() {
        return Screen.getInstance().getSquareScreenWidth() / FOV_CONSTANT;
    }

    public Point3d translate(double x, double y, double z) {
        return new Point3d(x() + x, y() + y, z() + z);
    }
}
