package org.csse220.game_engine.game_objects;

import org.csse220.game_engine.GameObject;
import org.csse220.game_engine.graphics.CompositeDrawable;
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
                new CompositeDrawable(new GamePose(0, 0, 0, 0),
                        new Cuboid(new GamePose(pose.x, pose.y, pose.z, 0), 30, 5, 5, Color.ORANGE),
                        new Cuboid(new GamePose(pose.x, pose.y + 15, pose.z, 0), 30, 5, 5, Color.ORANGE),

                        new Cuboid(new GamePose(pose.x - 10, pose.y + 7, pose.z + 5, 1.55), 30, 2.5, 5, Color.ORANGE),
                        new Cuboid(new GamePose(pose.x + 10, pose.y + 7, pose.z + 5, 1.55), 30, 2.5, 5, Color.ORANGE),

                        new Cuboid(new GamePose(pose.x, pose.y, pose.z + 7.5, 0), 30, 2.5, 5, Color.ORANGE),
                        new Cuboid(new GamePose(pose.x, pose.y + 15, pose.z + 7.5, 0), 30, 2.5, 5, Color.ORANGE),

                        new Cuboid(new GamePose(pose.x - 10, pose.y + 7, pose.z + 10, 1.55), 30, 2.5, 5, Color.ORANGE),
                        new Cuboid(new GamePose(pose.x + 10, pose.y + 7, pose.z + 10, 1.55), 30, 2.5, 5, Color.ORANGE),

                        new Cuboid(new GamePose(pose.x, pose.y, pose.z + 12.5, 0), 30, 2.5, 5, Color.ORANGE),
                        new Cuboid(new GamePose(pose.x, pose.y + 15, pose.z + 12.5, 0), 30, 2.5, 5, Color.ORANGE),

                        new Cuboid(new GamePose(pose.x - 10, pose.y + 7, pose.z + 15, 1.55), 30, 2.5, 5, Color.ORANGE),
                        new Cuboid(new GamePose(pose.x + 10, pose.y + 7, pose.z + 15, 1.55), 30, 2.5, 5, Color.ORANGE)
                ));

    }

    @Override
    public void onSolidCollision(GameObject other, GamePose moveDirection) {
        other.onHitByBonfire();
    }
}
