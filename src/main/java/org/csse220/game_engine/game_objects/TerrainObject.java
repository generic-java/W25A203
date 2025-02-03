package org.csse220.game_engine.game_objects;

import org.csse220.game_engine.GameObject;
import org.csse220.game_engine.graphics.Cuboid;

public class TerrainObject extends GameObject {
    public TerrainObject(Cuboid cuboid) {
        super(cuboid.toHitbox(), cuboid);
    }

    public void onCollide() {

    }
}
