package org.csse220.levels;

import org.csse220.game_engine.GameObject;
import org.csse220.game_engine.graphics.Cuboid;
import org.csse220.game_engine.graphics.Point3d;
import org.csse220.game_engine.kinematics.EmptyHitbox;

import java.awt.*;

public class Portal extends GameObject {

    public Portal() {
        super(EmptyHitbox.getInstance(), new Cuboid(new Point3d(0, 15, 0), 5, 5, 5, Color.BLUE));
    }

    @Override
    public void onCollide() {

    }
}
