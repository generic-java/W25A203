package org.csse220.game_engine.kinematics;

import org.csse220.game_engine.GameObject;
import org.csse220.game_engine.graphics.Drawable;

public class Trampoline extends GameObject {
    public Trampoline(Collideable collideable, Drawable drawable) {
        super(collideable, drawable);
    }

    @Override
    public void onCollide() {

    }
}
