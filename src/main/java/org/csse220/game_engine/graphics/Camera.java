package org.csse220.game_engine.graphics;

import org.csse220.game_engine.math_utils.Vector3d;

public class Camera {
    private static Camera instance = null;

    private double x;
    private double y;
    private double z;

    private double pitch;
    private double yaw;

    private Camera() {

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

    public double pitch() {
        return pitch;
    }

    public double yaw() {
        return yaw;
    }

    public static Camera getInstance() {
        if (instance == null) {
            instance = new Camera();
        }
        return instance;
    }

    public Vector3d getPosition() {
        return new Vector3d(x, y, z);
    }

    public void setPosition(Vector3d position) {
        x = position.x();
        y = position.y();
        z = position.z();
    }
}
