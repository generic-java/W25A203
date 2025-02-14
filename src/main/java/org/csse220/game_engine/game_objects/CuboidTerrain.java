package org.csse220.game_engine.game_objects;

import org.csse220.game_engine.GameObject;
import org.csse220.game_engine.graphics.Cuboid;
import org.csse220.game_engine.math_utils.GamePose;

public class CuboidTerrain extends GameObject {

    public CuboidTerrain(GamePose pose, Cuboid cuboid) {
        super(pose, cuboid.toHitbox(), cuboid);
    }

    public CuboidTerrain(Cuboid cuboid) {
        this(cuboid.getCenter().toGamePose(), cuboid);
    }
}
