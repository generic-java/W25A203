package org.csse220.game_engine;

import org.csse220.game_engine.graphics.PlaceableObject;
import org.csse220.game_engine.math.GamePose;

/**
 * A PlaceableObject that has a velocity and therefore can have a changing position.  Every GameObject is a PlaceableObject.
 */
public abstract class MovingObject extends PlaceableObject {

    private GamePose lastPose;
    private GamePose velocity;
    private double gravity;
    private static final double MAX_MOVE_TRANSLATION = 0.5;
    private static final GamePose PRECISION_ROUNDING = new GamePose(1, 1, 1, 3);
    private GamePose lastTranslation = new GamePose();
    private static final int CAP = 100;

    public MovingObject(GamePose pose, double gravity) {
        super(pose);
        lastPose = pose;
        velocity = new GamePose();
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

    public void update(double dt) {

    }

    @Override
    public void setPose(GamePose pose) {
        lastPose = getPose();
        super.setPose(pose);
    }

    protected GamePose lastPose() {
        return lastPose;
    }

    /**
     * The moveDirection instance provided tells the method how to move the GameElement.  If new GamePose(k,0,0,0,0,0) is passed
     * to this method, it will move this object k * its current velocity in that direction.
     */
    public void move(GamePose moveDirection, double dt) {
        if (dt > CAP) {
            dt = CAP;
        }
        if (moveDirection.z() > 0) {
            velocity = velocity.translateZ(-gravity * dt);
        }
        GamePose translation = new GamePose(
                velocity.x() * moveDirection.x(),
                velocity.y() * moveDirection.y(),
                velocity.z() * moveDirection.z(),
                velocity.yaw() * moveDirection.yaw()
        ).scale(dt);
        lastTranslation = translation;
        pose = translation.addTo(pose);
        GamePose roundingIncrement = moveDirection.scale(pose.round((int) moveDirection.dot(PRECISION_ROUNDING)).relativeTo(pose).dot(moveDirection));
        pose = pose.addTo(roundingIncrement);
    }

    public GamePose getLastTranslation() {
        return lastTranslation;
    }

    public void setVel(GamePose velocity) {
        this.velocity = velocity;
    }

    public void setXVel(double xVel) {
        velocity = new GamePose(xVel, velocity.y(), velocity.z(), velocity.yaw());
    }

    public void setYVel(double yVel) {
        velocity = new GamePose(velocity.x(), yVel, velocity.z(), velocity.yaw());
    }

    public void setZVel(double zVel) {
        velocity = new GamePose(velocity.x(), velocity.y(), zVel, velocity.yaw());
    }

    public void setYawVel(double yawVel) {
        velocity = new GamePose(velocity.x(), velocity.y(), velocity.z(), yawVel);
    }

    public GamePose velocity() {
        return velocity;
    }
}
