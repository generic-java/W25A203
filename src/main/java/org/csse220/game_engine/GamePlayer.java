package org.csse220.game_engine;

import org.csse220.game_engine.graphics.Drawable;
import org.csse220.game_engine.kinematics.Collideable;

public abstract class GamePlayer extends GameObject {
    public GamePlayer(Collideable collideable, Drawable drawable) {
        super(collideable, drawable);
    }
}
