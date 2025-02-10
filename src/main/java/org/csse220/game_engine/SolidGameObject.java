package org.csse220.game_engine;

import org.csse220.game_engine.graphics.Drawable;
import org.csse220.game_engine.kinematics.Collideable;
import org.csse220.game_engine.math_utils.GamePose;

public class SolidGameObject extends GameObject {
    private boolean touchingGround;
    private static final double JUMP_VEL = 0.3;
    private static final GamePose COLLISION_CONSTANTS = new GamePose(0.01, 0.01, 0.01, 0.005);

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
            setVelocity(velocity().translateZ(JUMP_VEL));
        }
    }

    @Override
    public void onCollide(GameObject other, GamePose moveDirection) {
        while (other.getCollideable().hasCollided(getCollideable())) {
            double moveSize = moveDirection.dot(velocity());
            double move = moveSize / Math.abs(moveSize);
            double collisionMultiplier = COLLISION_CONSTANTS.dot(moveDirection);
            setPose(moveDirection.scale(-collisionMultiplier * move).addTo(pose));
        }
        if (velocity().z() < 0 && moveDirection.z() > 0) {
            touchingGround = true;
            setZVel(0);
        }
    }

    public boolean touchingGround() {
        return touchingGround;
    }
}
