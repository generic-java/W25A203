package org.csse220.game_engine.characters;

import org.csse220.game_engine.graphics.Cuboid;
import org.csse220.game_engine.kinematics.Hitbox;
import org.csse220.game_engine.math_utils.GamePose;

import java.awt.*;

public class Lava extends LethalEnemy {
    public Lava(GamePose pose, double width, double height, double depth) {
        super(pose,
                new Hitbox(pose, width, height, depth),
                new Cuboid(pose, width, height, depth, Color.RED)
        );
    }
}
