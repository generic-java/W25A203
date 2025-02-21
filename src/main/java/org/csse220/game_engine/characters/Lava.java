package org.csse220.game_engine.characters;

import org.csse220.game_engine.graphics.Cuboid;
import org.csse220.game_engine.kinematics.Hitbox;
import org.csse220.game_engine.math.GamePose;

import java.awt.*;

/**
 * This describes a cuboid of lava with the desired dimensions and position.  If the player hits it, he dies.
 */
public class Lava extends LethalEnemy {
    public Lava(GamePose pose, double width, double height, double depth) {
        super(pose,
                new Hitbox(pose, width, height, depth),
                new Cuboid(pose, width, height, depth, Color.RED)
        );
    }
}
