package org.csse220.game_engine.characters;

import org.csse220.game_engine.GameObject;
import org.csse220.game_engine.SolidGameObject;
import org.csse220.game_engine.graphics.Drawable;
import org.csse220.game_engine.kinematics.Collideable;
import org.csse220.game_engine.math_utils.GamePose;

public class Enemy extends SolidGameObject {

    public Enemy(GamePose pose, Collideable hitbox, Drawable drawable) {
        super(pose, hitbox, drawable);
    }

    @Override
    public void onMovingCollision(GameObject other, GamePose moveDirection) {
        super.onMovingCollision(other, moveDirection);
        // TODO: enemy specific code here
    }
}
