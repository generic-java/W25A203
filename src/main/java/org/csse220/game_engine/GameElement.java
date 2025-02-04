package org.csse220.game_engine;

import org.csse220.game_engine.math_utils.Pose3d;
import org.csse220.game_engine.math_utils.Vector3d;

public abstract class GameElement {

    protected Pose3d pose;
    private Vector3d vel;
    private double gravity;

    public GameElement(double gravity) {
        pose = new Pose3d();
        vel = new Vector3d();
        this.gravity = gravity;
    }

    public GameElement() {
        this(0);
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

    public void move(double dt) {
        vel = vel.translateZ(-gravity * dt);
        pose = pose.translate(vel.scale(dt));
    }

    public void setVel(Vector3d vel) {
        this.vel = vel;
    }

    public void setXVel(double xVel) {
        vel = new Vector3d(xVel, vel.y(), vel.z());
    }

    public void setYVel(double yVel) {
        vel = new Vector3d(vel.x(), yVel, vel.z());
    }

    public void setZVel(double zVel) {
        vel = new Vector3d(vel.x(), vel.y(), zVel);
    }

    public Vector3d getVel() {
        return vel;
    }
}
