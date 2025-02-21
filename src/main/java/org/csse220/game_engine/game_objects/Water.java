package org.csse220.game_engine.game_objects;

import org.csse220.game_engine.Engine;
import org.csse220.game_engine.GameObject;
import org.csse220.game_engine.graphics.Cuboid;
import org.csse220.game_engine.kinematics.Hitbox;
import org.csse220.game_engine.math.GamePose;

import java.awt.*;

/**
 * A particle of water that the player elephant shoots out of its trunk.
 */
public class Water extends GameObject {
    private static final double MIN_Z = -100;

    public Water(GamePose pose, GamePose initialVelocity) {
        super(pose, new Hitbox(pose, 4, 4, 4), new Cuboid(pose, 2, 2, 2, new Color(50, 150, 230)));
        setGravity(0.001);
        setVel(initialVelocity);
    }

    private void kill() {
        Engine.getInstance().removeGameObject(this);
    }

    public void update(double dt) {
        if (pose.z() < MIN_Z) {
            kill();
        }
    }

    @Override
    public void onSoftCollision(GameObject other, GamePose moveDirection) {
        other.hitByWater();
        if (other != Engine.getInstance().getPlayer() && !(other instanceof Water)) {
            kill();
        }
    }

    @Override
    public boolean blocksMovement() {
        return false;
    }
}
