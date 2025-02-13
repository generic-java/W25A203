package org.csse220.game_engine.game_objects;

import org.csse220.game_engine.GameObject;
import org.csse220.game_engine.graphics.CompoundDrawable;
import org.csse220.game_engine.graphics.Cuboid;
import org.csse220.game_engine.kinematics.CompositeHitbox;
import org.csse220.game_engine.kinematics.Hitbox;
import org.csse220.game_engine.math_utils.GamePose;

import java.awt.*;

public class Bonfire extends GameObject {
    public Bonfire(GamePose pose) {

        super(
                pose,
                new CompositeHitbox(pose, new Hitbox(pose, 25, 25, 25)),
                new CompoundDrawable(pose,
                        new Cuboid(pose, pose.toPoint3d(), 25, 20, 25, new Color(94, 49, 16)),
                        new Cuboid(pose, pose.toPoint3d().translateZ(20), 25, 5, 25, new Color(247, 171, 64))));
    }
}
