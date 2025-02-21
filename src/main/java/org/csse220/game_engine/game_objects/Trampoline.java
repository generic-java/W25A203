package org.csse220.game_engine.game_objects;

import org.csse220.game_engine.GameObject;
import org.csse220.game_engine.graphics.Cuboid;
import org.csse220.game_engine.kinematics.Hitbox;
import org.csse220.game_engine.math.GamePose;

import java.awt.*;

/**
 * A purple block that makes the player bounce upon contact.
 */
public class Trampoline extends GameObject {
    public Trampoline(GamePose pose, double width, double height, double depth) {
        super(
                pose,
                new Hitbox(pose, width, height, depth),
                new Cuboid(pose, width, height, depth, new Color(180, 0, 230)));
    }

    @Override
    public void onSoftCollision(GameObject other, GamePose moveDirection) {
        other.hitByTrampoline();
    }

    @Override
    public boolean blocksMovement() {
        return false;
    }
}
