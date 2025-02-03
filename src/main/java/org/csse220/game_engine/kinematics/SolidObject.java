package org.csse220.game_engine.kinematics;

import org.csse220.game_engine.GameObject;
import org.csse220.game_engine.graphics.Drawable;

public class SolidObject extends GameObject {

    public SolidObject(Collideable collideable, Drawable drawable) {
        super(collideable, drawable);
    }

    @Override
    public void onCollide() {

    }
}
