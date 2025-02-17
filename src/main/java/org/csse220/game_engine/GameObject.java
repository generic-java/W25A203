package org.csse220.game_engine;

import org.csse220.game_engine.characters.PaperAirplane;
import org.csse220.game_engine.graphics.Drawable;
import org.csse220.game_engine.kinematics.Collideable;
import org.csse220.game_engine.math_utils.GamePose;

public class GameObject extends MovingObject {
    private final Collideable collideable;
    private final Drawable drawable;
    private final GamePose relativeCollideablePose;
    private final GamePose relativeDrawablePose;
    private double yaw;

    public GameObject(GamePose pose, Collideable collideable, Drawable drawable) {
        super(pose);
        this.collideable = collideable;
        this.drawable = drawable;
        if (hasCollideable()) {
            relativeCollideablePose = collideable.getPose().relativeTo(pose);
        } else {
            relativeCollideablePose = new GamePose();
        }
        if (hasDrawable()) {
            relativeDrawablePose = drawable.getPose().relativeTo(pose);
            if (this instanceof PaperAirplane) {
                //System.out.println("printing drone pose");
                //System.out.println(relativeDrawablePose);
            }
        } else {
            relativeDrawablePose = new GamePose();
        }
    }

    public final boolean hasCollideable() {
        return collideable != null;
    }

    public final boolean hasDrawable() {
        return drawable != null;
    }

    @Override
    public void setPose(GamePose pose) {
        super.setPose(pose);
        if (hasCollideable()) {
            collideable.setPose(relativeCollideablePose.addTo(pose));
        }
        if (hasDrawable()) {
            drawable.setPose(relativeDrawablePose.addTo(pose));
        }
    }

    @Override
    public void move(GamePose moveDirection, double dt) {
        super.move(moveDirection, dt);
        if (hasCollideable())
            collideable.setPose(relativeCollideablePose.addTo(pose));
        if (hasDrawable())
            drawable.setPose(relativeDrawablePose.addTo(pose));
    }

    public void hitByDrone() {
    }

    public void hitByLethalEnemy() {

    }

    public void hitByPlayer() {
    }

    public void hitByTrampoline() {

    }

    public double yaw() {
        return yaw;
    }

    public void incrementYaw(double increment) {
        yaw += increment;
    }

    public Collideable getCollideable() {
        return collideable;
    }

    public Drawable getDrawable() {
        return drawable;
    }

    public void onSolidCollision(GameObject other, GamePose moveDirection) {

    }

    public void onSoftCollision(GameObject other, GamePose moveDirection) {

    }

    public void reset() {

    }

    public void onHitByBonfire() {
    }

    ;

    public boolean blocksMovement() {
        return true;
    }

    public void pickUpFuel() {
    }


    //protected abstract boolean onCollide();
}
