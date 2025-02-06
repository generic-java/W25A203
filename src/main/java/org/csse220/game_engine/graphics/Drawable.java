package org.csse220.game_engine.graphics;

import org.csse220.game_engine.math_utils.GamePose;
import org.csse220.game_engine.math_utils.Vector3d;


public abstract class Drawable extends Placeable {
    public Drawable(GamePose pose) {
        super(pose);
    }

    abstract void draw(Vector3d camPose, double pitch, double yaw, boolean shade);
}
