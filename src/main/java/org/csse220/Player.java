package org.csse220;

import org.csse220.game_engine.GameObject;
import org.csse220.game_engine.graphics.Cuboid;
import org.csse220.game_engine.graphics.Point3d;
import org.csse220.game_engine.kinematics.Hitbox;
import org.csse220.game_engine.math_utils.GamePose;

import java.awt.*;

public class Player extends GameObject {

    public Player() {
        super(new GamePose(), new Hitbox(new GamePose(), 0, 0, 0), new Cuboid(new Point3d(0, 0, 0), 5, 5, 5, Color.ORANGE));
        setGravity(0 * 0.0003);
    }

    public boolean onCollide() {
        return false;
    }
}
