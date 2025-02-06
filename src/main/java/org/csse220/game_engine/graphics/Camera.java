package org.csse220.game_engine.graphics;

import org.csse220.game_engine.math_utils.CameraPose;
import org.csse220.game_engine.math_utils.PoseSupplier;

public class Camera implements PoseSupplier {
    private static Camera instance = null;

    private CameraPose position;

    private Camera() {
        position = new CameraPose();
    }

    public CameraPose getPosition() {
        return position;
    }

    public static Camera getInstance() {
        if (instance == null) {
            instance = new Camera();
        }
        return instance;
    }

    public void setXYZ(CameraPose pose) {
        position = new CameraPose(pose.x(), pose.y(), pose.z(), position.pitch(), position.yaw());
    }

    public void setPosition(CameraPose pose) {
        this.position = pose;
    }

    public void setPitch(double pitch) {
        position = new CameraPose(position.x(), position.y(), position.z(), pitch, position.yaw());
    }

    public void setYaw(double yaw) {
        position = new CameraPose(position.x(), position.y(), position.z(), position.pitch(), yaw);
    }

    @Override
    public CameraPose getPose() {
        return position;
    }
}
