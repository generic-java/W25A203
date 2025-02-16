package org.csse220;

import org.csse220.game_engine.Engine;
import org.csse220.game_engine.GameObject;
import org.csse220.game_engine.characters.GamePlayer;
import org.csse220.game_engine.graphics.Cuboid;
import org.csse220.game_engine.kinematics.Hitbox;
import org.csse220.game_engine.math_utils.GamePose;

import java.awt.*;

public class Player extends GamePlayer {
    private int fuelCounter;
    private static final int MAX_HEALTH = 3;
    private int health = MAX_HEALTH;
    private static final double MIN_Z = -500.0;

    public Player() {
        super(new GamePose(), new Hitbox(new GamePose(), 5, 5, 5), new Cuboid(new GamePose(0, 0, 0, 0), 5, 5, 5, Color.ORANGE));
        setGravity(0.0006);
        setPose(new GamePose(0, 0, 15, 0));
    }

    public void attackedByEnemy() {

    }

    public void die() {
        Engine.getInstance().getSoundPlayer().deathSound();
        for (int i = 0; i < 3; i++) {
            getDrawable().hide();
            Engine.getInstance().render();
            trySleep(100);
            getDrawable().show();
            Engine.getInstance().render();
            trySleep(100);
        }
        Engine.getInstance().resetLevel();
        health = MAX_HEALTH;
    }

    private void trySleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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

    @Override
    public void softCollision(GameObject other, GamePose pose) {
        other.hitByPlayer();
    }
}
