package org.csse220.game_engine.graphics;

import org.csse220.game_engine.math.CameraPose;
import org.csse220.game_engine.math.GamePose;

public class Camera {
    private static Camera instance = null;

    private CameraPose pose;

    private Camera() {
        pose = new CameraPose();
    }

    public static Camera getInstance() {
        if (instance == null) {
            instance = new Camera();
        }
        return instance;
    }

    public void setXYZ(CameraPose pose) {
        this.pose = new CameraPose(pose.x(), pose.y(), pose.z(), this.pose.pitch(), this.pose.yaw());
    }

    public void setPose(GamePose pose) {
        this.pose = new CameraPose(pose.x(), pose.y(), pose.z(), this.pose.pitch(), pose.yaw());
    }

    public void setPosition(CameraPose pose) {
        this.pose = pose;
    }

    public void setPitch(double pitch) {
        pose = new CameraPose(pose.x(), pose.y(), pose.z(), pitch, pose.yaw());
    }

    public void setYaw(double yaw) {
        pose = new CameraPose(pose.x(), pose.y(), pose.z(), pose.pitch(), yaw);
    }

    public CameraPose getPose() {
        return pose;
    }
}
