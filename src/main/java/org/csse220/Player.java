package org.csse220;

import org.csse220.game_engine.SolidGameObject;
import org.csse220.game_engine.graphics.Cuboid;
import org.csse220.game_engine.graphics.Point3d;
import org.csse220.game_engine.kinematics.Hitbox;
import org.csse220.game_engine.math_utils.GamePose;

import java.awt.*;

public class Player extends SolidGameObject {

    public Player() {
        super(new GamePose(), new Hitbox(new GamePose(), 5, 5, 5), new Cuboid(new Point3d(0, 0, 0), 5, 5, 5, Color.ORANGE));
        setGravity(0 * 0.03);
    }
}
