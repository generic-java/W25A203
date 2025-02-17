package org.csse220.game_engine.game_objects;

import org.csse220.game_engine.GameObject;
import org.csse220.game_engine.graphics.CompositeDrawable;
import org.csse220.game_engine.graphics.Cuboid;
import org.csse220.game_engine.kinematics.Hitbox;
import org.csse220.game_engine.math_utils.GamePose;

import java.awt.*;

public class GrassBlock extends GameObject {
    public static final double TOP_PERCENT = 0.2;

    public GrassBlock(GamePose pose, double width, double height, double depth) {
        super(
                pose,
                new Hitbox(pose, width, height, depth),
                new CompositeDrawable(
                        pose,
                        new Cuboid(pose.translateZ(-TOP_PERCENT / 2 * height + height / 2), width, height * TOP_PERCENT, depth, new Color(90, 250, 90)),
                        new Cuboid(pose.translateZ(-TOP_PERCENT * height / 2), width, height * (1 - TOP_PERCENT), depth, new Color(90, 50, 50))
                )
        );
    }
}
