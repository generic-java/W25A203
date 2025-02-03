package org.csse220.game_engine.kinematics;

import org.csse220.game_engine.GameObject;
import org.csse220.game_engine.graphics.Drawable;

public class Lava extends GameObject {
    public Lava(Collideable collideable, Drawable drawable) {
        super(collideable, drawable);
    }

    @Override
    public void onCollide() {

    }
}
