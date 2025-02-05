package org.csse220.game_engine.graphics;

import org.csse220.game_engine.math_utils.Vector3d;


public interface Drawable {
    void draw(Vector3d camPose, double pitch, double yaw, boolean shade);
}
