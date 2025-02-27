package org.csse220.game_engine.characters;

import org.csse220.game_engine.GameObject;
import org.csse220.game_engine.graphics.Drawable;
import org.csse220.game_engine.kinematics.Collideable;
import org.csse220.game_engine.math.GamePose;

/**
 * This is a marker class for all enemies.
 */
public class Enemy extends GameObject {

    public Enemy(GamePose pose, Collideable hitbox, Drawable drawable) {
        super(pose, hitbox, drawable);
    }

}
