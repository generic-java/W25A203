package org.csse220.game_engine;

import org.csse220.game_engine.graphics.Drawable;
import org.csse220.game_engine.kinematics.Collideable;
import org.csse220.game_engine.math_utils.GamePose;

public class SolidGameObject extends GameObject {
    private boolean touchingGround = false;
    private static final double JUMP_VEL = 0.3;
    private static final GamePose COLLISION_CONSTANTS = new GamePose(0.001, 0.001, 0.001, 0.001);

    public SolidGameObject(GamePose pose, Collideable collideable, Drawable drawable) {
        super(pose, collideable, drawable);
    }

    @Override
    public void move(GamePose moveDirection, double dt) {
        GamePose lastPose = pose;
        super.move(moveDirection, dt);
        if (moveDirection.z() > 0) {
            touchingGround = false;
        }
        if (moveDirection.z() == 0) {
            if (pose.relativeTo(lastPose).z() != 0) {
                throw new RuntimeException(String.valueOf(pose.relativeTo(lastPose).z()));
            }
        }
    }

    public void jump() {
        if (touchingGround) {
            touchingGround = false;
            setVel(velocity().translateZ(JUMP_VEL));
        }
    }

    @Override
    public void onMovingCollision(GameObject other, GamePose moveDirection) {
        GamePose translation = velocity();
        if (translation.equals(new GamePose(0, 0, 0, 0))) {
            return;
        }
        double moveSize = moveDirection.dot(translation);
        double translationPolarity = moveSize / Math.abs(moveSize);
        double collisionMultiplier = COLLISION_CONSTANTS.dot(moveDirection);

        GamePose increment = moveDirection.scale(-collisionMultiplier * translationPolarity);
        int precision = moveDirection.yaw() > 0 ? 2 : 3;

        int i = 0;
        while (other.getCollideable().hasCollided(getCollideable())) {
            setPose(increment.addTo(pose));
            i++;
            if (i > 1000000) {
                throw new RuntimeException(toString());
            }
        }

        GamePose roundingIncrement = moveDirection.scale(pose.round(precision).relativeTo(pose).dot(moveDirection));
        if (roundingIncrement.dot(moveDirection) * -translationPolarity < 0) { // If the rounding increment is in the opposite direction as the correction movement, add/remove 10^(-precision).  Otherwise, the object would be colliding again
            roundingIncrement = roundingIncrement.addTo(moveDirection.scale(-translationPolarity * Math.pow(10, -precision)));
        }
        setPose(pose.addTo(roundingIncrement));

        if (moveDirection.z() > 0) {
            if (velocity().z() < 0) {
                touchingGround = true;
            }
            setZVel(0.0);
        }
    }

    public boolean touchingGround() {
        return touchingGround;
    }
}
