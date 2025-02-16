package org.csse220.game_engine.graphics;

import org.csse220.game_engine.math_utils.CameraPose;
import org.csse220.game_engine.math_utils.GamePose;
import org.csse220.game_engine.math_utils.Vector3d;

public class Point3d extends Vector3d {
    private static double FOV;
    private static double FOV_CONSTANT;

    static {
        setFOV(Math.toRadians(100));
    }

    public static void setFOV(double FOV) {
        Point3d.FOV = FOV;
        Point3d.FOV_CONSTANT = 2 * Math.tan(FOV / 2); // The multiple of 2 isn't really associated with the FOV - it's just a constant factor that divides the screen width by 2 in the long run
    }

    public static double getFOV() {
        return FOV;
    }

    private Vector3d relativePose;

    public Point3d(double x, double y, double z) {
        super(x, y, z);
        calculateRelativePosition();
    }

    public Point3d(Vector3d vector) {
        super(vector.x(), vector.y(), vector.z());
        calculateRelativePosition();
    }

    public double relativeX() {
        return relativePose.x();
    }

    public double relativeY() {
        return relativePose.y();
    }

    public double relativeZ() {
        return relativePose.z();
    }

    public Vector3d relativePose() {
        return relativePose;
    }

    public void calculateRelativePosition() {
        CameraPose camPose = Camera.getInstance().getPose();
        Vector3d rotatedPos = rotatePitchYaw(camPose);
        relativePose = rotatedPos.relativeTo(camPose);
    }

    public ProjectedPoint project() {
        calculateRelativePosition();
        return project(relativeX(), relativeY(), relativeZ());
    }

    public static ProjectedPoint project(double relX, double relY, double relZ) {
        return new ProjectedPoint(
                projectionConstant() * relX / relY,
                projectionConstant() * relZ / relY
        );
    }

    private static double precisionRound(double num, int precision) {
        double factor = Math.pow(10, precision);
        return Math.round(num * factor) / factor;
    }

    public static double projectionConstant() {
        return Screen.getInstance().getSquareScreenWidth() / FOV_CONSTANT;
    }

    public Point3d translate(double x, double y, double z) {
        return new Point3d(super.translate(x, y, z));
    }

    public Point3d translate(Vector3d translation) {
        return new Point3d(super.translate(translation));
    }

    public Point3d translateZ(double z) {
        return new Point3d(super.translateZ(z));
    }

    public Point3d translateY(double y) {
        return new Point3d(super.translateY(y));
    }

    public Point3d translateX(double x) {
        return new Point3d(super.translateX(x));
    }


    public Point3d relativeTo(Point3d other) {
        return new Point3d(super.relativeTo(other));
    }

    public Point3d relativeTo(GamePose other) {
        return new Point3d(super.relativeTo(other).rotateYaw(-other.yaw()));
    }
}
