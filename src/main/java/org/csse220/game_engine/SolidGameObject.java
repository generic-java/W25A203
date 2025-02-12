package org.csse220.game_engine;

import org.csse220.game_engine.graphics.Drawable;
import org.csse220.game_engine.kinematics.Collideable;
import org.csse220.game_engine.math_utils.GamePose;

public class SolidGameObject extends GameObject {
    private boolean touchingGround = false;
    private static final double JUMP_VEL = 0.3;
    private static final GamePose COLLISION_CONSTANTS = new GamePose(0.005, 0.005, 0.005, 0.005);

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

        if (moveSize == 0) {
            return;
            //throw new RuntimeException("Why am I colliding when I have no velocity?");
        }
        double move = moveSize / Math.abs(moveSize);
        double collisionMultiplier = COLLISION_CONSTANTS.dot(moveDirection);
        int i = 0;
        GamePose initial = getPose();
        GamePose increment = moveDirection.scale(-collisionMultiplier * move);
        while (other.getCollideable().hasCollided(getCollideable())) {
            setPose(increment.addTo(pose));
            if (i > 1000000) {
                System.out.println(moveDirection.scale(-collisionMultiplier * move));
                System.out.println(getPose());
                System.out.println(initial);
                return;
                //throw new RuntimeException("uh oh");
            }
            i++;
        }
        setPose(pose.round(2));
        setPose(moveDirection.scale(-collisionMultiplier * move).addTo(pose));
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
