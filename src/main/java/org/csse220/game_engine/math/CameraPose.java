package org.csse220.game_engine.math;

/**
 * Describes a camera position.  Has x, y, z, pitch, and yaw.
 */
public class CameraPose extends GamePose {
    private final double pitch;

    public CameraPose(double x, double y, double z, double pitch, double yaw) {
        super(x, y, z, yaw);
        this.pitch = pitch;
    }

    public CameraPose() {
        super();
        pitch = 0;
    }

    public double getPitch() {
        return pitch;
    }

    public double pitch() {
        return pitch;
    }
}
