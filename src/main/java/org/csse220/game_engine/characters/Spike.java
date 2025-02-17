package org.csse220.game_engine.characters;

import org.csse220.game_engine.graphics.CompositeDrawable;
import org.csse220.game_engine.graphics.Face;
import org.csse220.game_engine.kinematics.Hitbox;
import org.csse220.game_engine.math_utils.GamePose;

import java.awt.*;

public class Spike extends LethalEnemy {
    public Spike(GamePose pose) {
        super(pose,
                new Hitbox(pose, 5, 5, 5),
                new CompositeDrawable(
                        pose,
                        new Face(
                                pose,
                                pose.translate(1.5, -1.5, 0).rotateYaw(pose, pose.yaw()).toPoint3d(),
                                pose.translate(-1.5, -1.5, 0).rotateYaw(pose, pose.yaw()).toPoint3d(),
                                pose.translateZ(4).toPoint3d(),
                                Color.LIGHT_GRAY
                        ),
                        new Face(
                                pose,
                                pose.translate(1.5, 1.5, 0).rotateYaw(pose, pose.yaw()).toPoint3d(),
                                pose.translate(-1.5, 1.5, 0).rotateYaw(pose, pose.yaw()).toPoint3d(),
                                pose.translateZ(4).toPoint3d(),
                                Color.LIGHT_GRAY
                        ),
                        new Face(
                                pose,
                                pose.translate(-1.5, 1.5, 0).rotateYaw(pose, pose.yaw()).toPoint3d(),
                                pose.translate(-1.5, -1.5, 0).rotateYaw(pose, pose.yaw()).toPoint3d(),
                                pose.translateZ(4).toPoint3d(),
                                Color.LIGHT_GRAY
                        ),
                        new Face(
                                pose,
                                pose.translate(1.5, 1.5, 0).rotateYaw(pose, pose.yaw()).toPoint3d(),
                                pose.translate(1.5, -1.5, 0).rotateYaw(pose, pose.yaw()).toPoint3d(),
                                pose.translateZ(4).toPoint3d(),
                                Color.LIGHT_GRAY
                        )
                ));
    }
}
