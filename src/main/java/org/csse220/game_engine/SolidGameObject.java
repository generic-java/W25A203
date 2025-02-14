package org.csse220.game_engine;

import org.csse220.game_engine.graphics.Drawable;
import org.csse220.game_engine.kinematics.Collideable;
import org.csse220.game_engine.math_utils.GamePose;

public class SolidGameObject extends GameObject {
    private boolean touchingGround = false;
    private static final double JUMP_VEL = 0.3;
    private static final GamePose COLLISION_CONSTANTS = new GamePose(0.01, 0.01, 0.01, 0.01);

    public SolidGameObject(GamePose pose, Collideable collideable, Drawable drawable) {
        super(pose, collideable, drawable);
    }

    @Override
    public void move(GamePose moveDirection, double dt) {
        super.move(moveDirection, dt);
        if (moveDirection.z() > 0) {
            touchingGround = false;
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
        double move = moveSize / Math.abs(moveSize);
        double collisionMultiplier = COLLISION_CONSTANTS.dot(moveDirection);

        GamePose increment = moveDirection.scale(-collisionMultiplier * move);
        int precision = moveDirection.yaw() > 0 ? 2 : 1;
        GamePose roundingIncrement = moveDirection.scale(pose.round(precision).relativeTo(pose).dot(moveDirection));
        setPose(pose.fullTranslation(roundingIncrement));
        while (other.getCollideable().hasCollided(getCollideable())) {
            setPose(increment.addTo(pose));
        }
        if (moveDirection.z() > 0) {
            if (velocity().z() < 0) {
                touchingGround = true;
            }
            setZVel(0);
        }
    }

    public boolean touchingGround() {
        return touchingGround;
    }
}
