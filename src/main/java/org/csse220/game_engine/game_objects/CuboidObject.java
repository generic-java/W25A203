package org.csse220.game_engine.game_objects;

import org.csse220.game_engine.GameObject;
import org.csse220.game_engine.graphics.Cuboid;
import org.csse220.game_engine.kinematics.EmptyHitbox;

public class CuboidObject extends GameObject {
    public CuboidObject(Cuboid cuboid) {
        super(EmptyHitbox.getInstance(), cuboid);
    }
}
