package org.csse220.game_engine;

import org.csse220.game_engine.math_utils.Pose3d;
import org.csse220.game_engine.math_utils.Vector3d;

public abstract class GameElement {

    protected Pose3d pose;
    private Pose3d vel;
    private double gravity;

    public GameElement(Pose3d pose, double gravity) {
        this.pose = pose;
        vel = new Pose3d();
        this.gravity = gravity;
    }

    public GameElement(Pose3d pose) {
        this(pose, 0);
    }

    public GameElement() {
        this(new Pose3d());
    }

    public void setGravity(double gravity) {
        this.gravity = gravity;
    }

    public void setPose(Pose3d pose) {
        this.pose = pose;
    }

    public Pose3d getPose() {
        return pose;
    }

    /**
     * The moveDirection instance provided tells the method how to move the GameElement.  If new Pose3d(k,0,0,0,0,0) is passed
     * to this method, it will move this object k * its current velocity in that direction.
     */
    public void move(Pose3d moveDirection, double dt) {
        if (moveDirection.z() > 0) {
            vel = vel.translateZ(-gravity * dt);
        }
        Pose3d translation = new Pose3d(
                vel.x() * moveDirection.x(),
                vel.y() * moveDirection.y(),
                vel.z() * moveDirection.z(),
                vel.pitch() * moveDirection.pitch(),
                vel.roll() * moveDirection.roll(),
                vel.yaw() * moveDirection.yaw()
        ).scale(dt);
        pose = pose.addTo(translation);
    }

    public void setVel(Pose3d vel) {
        this.vel = vel;
    }

    public void setXVel(double xVel) {
        vel = new Pose3d(xVel, vel.y(), vel.z(), vel.pitch(), vel.roll(), vel.yaw());
    }

    public void setYVel(double yVel) {
        vel = new Pose3d(vel.x(), yVel, vel.z(), vel.pitch(), vel.roll(), vel.yaw());
    }

    public void setZVel(double zVel) {
        vel = new Pose3d(vel.x(), vel.y(), zVel, vel.pitch(), vel.roll(), vel.yaw());
    }

    public void setPitchVel(double pitchVel) {
        vel = new Pose3d(vel.x(), vel.y(), vel.z(), pitchVel, vel.roll(), vel.yaw());
    }

    public void setRollVel(double rollVel) {
        vel = new Pose3d(vel.x(), vel.y(), vel.z(), vel.pitch(), rollVel, vel.yaw());
    }

    public void setYawVel(double yawVel) {
        vel = new Pose3d(vel.x(), vel.y(), vel.z(), vel.pitch(), vel.roll(), yawVel);
    }

    public Vector3d getVel() {
        return vel;
    }
}
