package org.csse220.game_engine.characters;

import org.csse220.game_engine.SolidGameObject;
import org.csse220.game_engine.graphics.Drawable;
import org.csse220.game_engine.kinematics.Collideable;
import org.csse220.game_engine.math_utils.GamePose;

public abstract class GamePlayer extends SolidGameObject {
    public GamePlayer(GamePose pose, Collideable collideable, Drawable drawable) {
        super(pose, collideable, drawable);
    }

    abstract public void attackedByEnemy();
}
