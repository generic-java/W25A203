package org.csse220.game_engine;

import org.csse220.game_engine.graphics.PlaceableObject;
import org.csse220.game_engine.math_utils.GamePose;
import org.csse220.game_engine.math_utils.Vector3d;

public abstract class MovingObject extends PlaceableObject {

    private GamePose vel;
    private double gravity;

    public MovingObject(GamePose pose, double gravity) {
        super(pose);
        vel = new GamePose();
        this.gravity = gravity;
    }

    public MovingObject(GamePose pose) {
        this(pose, 0);
    }

    public MovingObject() {
        this(new GamePose());
    }

    public void setGravity(double gravity) {
        this.gravity = gravity;
    }

    /**
     * The moveDirection instance provided tells the method how to move the GameElement.  If new Pose3d(k,0,0,0,0,0) is passed
     * to this method, it will move this object k * its current velocity in that direction.
     */
    public void move(GamePose moveDirection, double dt) {
        if (moveDirection.z() > 0) {
            vel = vel.translateZ(-gravity * dt);
        }
        GamePose translation = new GamePose(
                vel.x() * moveDirection.x(),
                vel.y() * moveDirection.y(),
                vel.z() * moveDirection.z(),
                vel.yaw() * moveDirection.yaw()
        ).scale(dt);
        pose = translation.addTo(pose);
    }

    public void setVel(GamePose vel) {
        this.vel = vel;
    }

    public void setXVel(double xVel) {
        vel = new GamePose(xVel, vel.y(), vel.z(), vel.yaw());
    }

    public void setYVel(double yVel) {
        vel = new GamePose(vel.x(), yVel, vel.z(), vel.yaw());
    }

    public void setZVel(double zVel) {
        vel = new GamePose(vel.x(), vel.y(), zVel, vel.yaw());
    }

    public void setYawVel(double yawVel) {
        vel = new GamePose(vel.x(), vel.y(), vel.z(), yawVel);
    }

    public Vector3d getVel() {
        return vel;
    }
}
