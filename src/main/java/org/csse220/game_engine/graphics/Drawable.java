package org.csse220.game_engine.graphics;

import org.csse220.game_engine.math_utils.GamePose;
import org.csse220.game_engine.math_utils.Vector3d;

import java.awt.*;


public abstract class Drawable extends PlaceableObject {
    protected final Color color;

    public Drawable(GamePose pose, Color color) {
        super(pose);
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public abstract void draw(Vector3d camPose, double pitch, double yaw, boolean shade);
}
