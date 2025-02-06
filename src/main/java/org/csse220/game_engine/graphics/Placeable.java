package org.csse220.game_engine.graphics;

import org.csse220.game_engine.math_utils.GamePose;

public class Placeable {
    protected GamePose pose;

    public Placeable(GamePose pose) {
        this.pose = pose;
    }

    public Placeable() {
        this(new GamePose());
    }

    public void setPose(GamePose pose) {
        this.pose = pose;
    }

    public final GamePose getPose() {
        return pose;
    }
}
