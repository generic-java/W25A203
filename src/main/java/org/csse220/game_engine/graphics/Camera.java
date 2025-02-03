package org.csse220.game_engine.graphics;

import org.csse220.game_engine.math_utils.Pose3d;
import org.csse220.game_engine.math_utils.PoseSupplier;

public class Camera implements PoseSupplier {
    private static Camera instance = null;

    private Pose3d position;

    private Camera() {
        position = new Pose3d();
    }

    public Pose3d getPosition() {
        return position;
    }

    public static Camera getInstance() {
        if (instance == null) {
            instance = new Camera();
        }
        return instance;
    }

    public void setXYZ(Pose3d pose) {
        position = new Pose3d(pose.x(), pose.y(), pose.z(), position.pitch(), position.roll(), position.yaw());
    }

    public void setPosition(Pose3d pose) {
        this.position = pose;
    }

    public void setPitch(double pitch) {
        position = new Pose3d(position.x(), position.y(), position.z(), pitch, position.roll(), position.yaw());
    }

    public void setYaw(double yaw) {
        position = new Pose3d(position.x(), position.y(), position.z(), position.pitch(), position.roll(), yaw);
    }

    @Override
    public Pose3d getPose() {
        return position;
    }
}
