package org.csse220;

import org.csse220.game_engine.GameObject;
import org.csse220.game_engine.graphics.Cuboid;
import org.csse220.game_engine.graphics.Point3d;
import org.csse220.game_engine.kinematics.EmptyHitbox;

import java.awt.*;

public class Player extends GameObject {

    public Player() {
        super(EmptyHitbox.getInstance(), new Cuboid(new Point3d(0, 0, 0), 5, 5, 5, Color.ORANGE));
        setGravity(0.0003);
    }

    public void onCollide() {

    }
}
