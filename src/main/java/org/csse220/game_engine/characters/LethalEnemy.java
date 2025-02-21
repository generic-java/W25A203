package org.csse220.game_engine.characters;

import org.csse220.game_engine.GameObject;
import org.csse220.game_engine.graphics.Drawable;
import org.csse220.game_engine.kinematics.Collideable;
import org.csse220.game_engine.math.GamePose;

/**
 * Anything that extends this class will kill the player upon contact.
 */
public abstract class LethalEnemy extends Enemy {
    public LethalEnemy(GamePose pose, Collideable collideable, Drawable drawable) {
        super(pose, collideable, drawable);
    }

    @Override
    public void onSoftCollision(GameObject other, GamePose moveDirection) {
        other.hitByLethalEnemy();
    }

    @Override
    public boolean blocksMovement() {
        return false;
    }
}
