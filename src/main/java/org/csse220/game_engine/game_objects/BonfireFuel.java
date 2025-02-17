package org.csse220.game_engine.game_objects;

import org.csse220.game_engine.GameObject;
import org.csse220.game_engine.graphics.CompositeDrawable;
import org.csse220.game_engine.graphics.Cuboid;
import org.csse220.game_engine.kinematics.CompositeHitbox;
import org.csse220.game_engine.kinematics.Hitbox;
import org.csse220.game_engine.math_utils.GamePose;

import java.awt.*;

public class BonfireFuel extends GameObject {

    private boolean isHidden = false;

    public BonfireFuel(GamePose pose) {

        super(
                pose,
                new CompositeHitbox(pose, new Hitbox(pose, 1, 4, 1)),
                new CompositeDrawable(pose,
                        new Cuboid(pose, pose, 1, 3, 1, new Color(94, 49, 16)),
                        new Cuboid(pose, pose.translateZ(2), 1, 1, 1, new Color(247, 171, 64))));
    }


    @Override
    public void onSoftCollision(GameObject other, GamePose moveDirection) {
        if (!isHidden) {
            other.pickUpFuel();
            isHidden = true;
            getDrawable().hide();
        }


    }

    public void deleteSelf() {

    }

    @Override
    public void reset() {
        this.isHidden = false;
        getDrawable().show();
    }

    @Override
    public boolean blocksMovement() {
        return false;
    }
}
