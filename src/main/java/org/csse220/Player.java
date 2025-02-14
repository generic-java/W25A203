package org.csse220;

import org.csse220.game_engine.Engine;
import org.csse220.game_engine.characters.GamePlayer;
import org.csse220.game_engine.graphics.Cuboid;
import org.csse220.game_engine.graphics.Point3d;
import org.csse220.game_engine.kinematics.Hitbox;
import org.csse220.game_engine.math_utils.GamePose;

import java.awt.*;

public class Player extends GamePlayer {
    private int fuelCounter;
    private static final int MAX_HEALTH = 3;
    private int health = MAX_HEALTH;
    private static final double MIN_Z = -500.0;

    public Player() {
        super(new GamePose(), new Hitbox(new GamePose(), 5, 5, 5), new Cuboid(new Point3d(0, 0, 0), 5, 5, 5, Color.ORANGE));
        setGravity(0.0006);
        setPose(new GamePose(0, 0, 15, 0));
    }

    public void attackedByEnemy() {

    }

    public void die() {
        for (int i = 0; i < 3; i++) {
            getDrawable().hide();
            Engine.getInstance().render();
            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
                //TODO DEAL WITH IT
            }
            getDrawable().show();
            Engine.getInstance().render();
            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
                //TODO DEAL WITH IT
            }
        }
        Engine.getInstance().resetLevel();
        health = MAX_HEALTH;
    }

    @Override
    public void update() {
        super.update();
        if (getPose().z < MIN_Z) {
            die();
        }
    }

    public int getFuelCounter() {
        return fuelCounter;
    }

    @Override
    public void pickUpFuel() {
        fuelCounter++;
    }

    public int getHealth() {
        return health;
    }
}
