package org.csse220.game_engine;

import org.csse220.game_engine.graphics.Drawable;
import org.csse220.game_engine.kinematics.Collideable;
import org.csse220.game_engine.math_utils.GamePose;

public class SolidGameObject extends GameObject {
    private boolean touchingGround;
    private static final double COLLISION_MULTIPLIER = 0.5;
    private static final GamePose COLLISION_CONSTANTS = new GamePose(0.5, 0.5, 0.5, 0.05);

    public SolidGameObject(GamePose pose, Collideable collideable, Drawable drawable) {
        super(pose, collideable, drawable);
    }

    @Override
    public void onCollide(GameObject other, GamePose moveDirection) {
        while (other.getCollideable().hasCollided(getCollideable())) {
            double moveSize = moveDirection.dot(velocity());
            double move = moveSize / Math.abs(moveSize);
            double collisionMultiplier = COLLISION_CONSTANTS.dot(moveDirection);
            setPose(moveDirection.scale(-collisionMultiplier * move).addTo(pose));
        }
    }
}
