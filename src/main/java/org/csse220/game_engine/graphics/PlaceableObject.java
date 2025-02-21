package org.csse220.game_engine.graphics;

import org.csse220.game_engine.math.GamePose;

/**
 * Every GameObject extends this.  Placeable objects have a position.
 */
public class PlaceableObject {
    protected GamePose pose;

    public PlaceableObject(GamePose pose) {
        this.pose = pose;
    }

    public PlaceableObject() {
        this(new GamePose());
    }

    public void setPose(GamePose pose) {
        this.pose = pose;
    }

    public final GamePose getPose() {
        return pose;
    }
}
