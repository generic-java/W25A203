package org.csse220;

import org.csse220.game_engine.characters.GamePlayer;
import org.csse220.game_engine.graphics.Cuboid;
import org.csse220.game_engine.graphics.Point3d;
import org.csse220.game_engine.kinematics.Hitbox;
import org.csse220.game_engine.math_utils.GamePose;

import java.awt.*;

public class Player extends GamePlayer {
    private int fuelCounter;

    public Player() {
        super(new GamePose(), new Hitbox(new GamePose(), 5, 5, 5), new Cuboid(new Point3d(0, 0, 0), 5, 5, 5, Color.ORANGE));
        setGravity(0.0006);
        setPose(new GamePose(0, 0, 15, 0));
    }

    public void attackedByEnemy() {

    }

    public int getFuelCounter() {
        return fuelCounter;
    }

    @Override
    public void pickUpFuel() {
        fuelCounter++;
    }
}
