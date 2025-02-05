package org.csse220;

import org.csse220.game_engine.GameObject;
import org.csse220.game_engine.graphics.Cuboid;
import org.csse220.game_engine.graphics.Point3d;
import org.csse220.game_engine.kinematics.Hitbox;
import org.csse220.game_engine.math_utils.Pose3d;

import java.awt.*;

public class Player extends GameObject {

    public Player() {
        super(new Hitbox(new Pose3d(), 0, 0, 0), new Cuboid(new Point3d(0, 0, 0), 5, 5, 5, Color.ORANGE));
        setGravity(0.0003);
    }

    public void onCollide() {

    }

    @Override
    public boolean onCollide(Pose3d moveDirection) {
        return false;
    }
}
