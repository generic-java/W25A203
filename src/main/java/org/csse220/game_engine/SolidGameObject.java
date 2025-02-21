package org.csse220.game_engine;

import org.csse220.game_engine.graphics.Drawable;
import org.csse220.game_engine.kinematics.Collideable;
import org.csse220.game_engine.math.GamePose;

/**
 * A GameObject that collides with terrain and stops upon hitting it.
 */
public class SolidGameObject extends GameObject {
    private boolean touchingGround = false;
    private static final double JUMP_VEL = 0.3;
    private static final GamePose COLLISION_CONSTANTS = new GamePose(0.1, 0.1, 0.1, 0.001);

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
    public void onSolidCollision(GameObject other, GamePose moveDirection) {
        GamePose translation = getLastTranslation();
        if (translation.equals(new GamePose(0, 0, 0, 0))) {
            return;
        }
        double moveSize = moveDirection.dot(translation);
        double translationPolarity = moveSize / Math.abs(moveSize);
        double collisionMultiplier = COLLISION_CONSTANTS.dot(moveDirection);

        GamePose increment = moveDirection.scale(-collisionMultiplier * translationPolarity);

        while (other.getCollideable().hasCollided(getCollideable())) {
            setPose(increment.addTo(pose));
        }

        if (moveDirection.z() > 0) {
            if (velocity().z() < 0) {
                touchingGround = true;
            }
            setZVel(-0.0);
        }
    }


    public void hitByTrampoline() {
        if (velocity().z() <= 0) {
            setZVel(JUMP_VEL * 2);
            Engine.getInstance().getSoundPlayer().trampolineSound();
        }
    }

    public boolean touchingGround() {
        return touchingGround;
    }
}
