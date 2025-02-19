package org.csse220;

import org.csse220.game_engine.Engine;
import org.csse220.game_engine.GameObject;
import org.csse220.game_engine.characters.Elephant;
import org.csse220.game_engine.characters.GamePlayer;
import org.csse220.game_engine.game_objects.Water;
import org.csse220.game_engine.graphics.Screen;
import org.csse220.game_engine.kinematics.Hitbox;
import org.csse220.game_engine.math.GamePose;

public class Player extends GamePlayer {
    private int fuelCount;
    private static final int MAX_HEALTH = 3;
    private int health = MAX_HEALTH;
    private static final double MIN_Z = -500.0;
    private static final double RANDOM_WATER = 0.3;
    private static final GamePose WATER_LAUNCH_VEL = new GamePose(0, 0.05, 0.25);

    public Player() {
        super(new GamePose(), new Hitbox(new GamePose(), 4, 15, 4), new Elephant(new GamePose()));
        setGravity(0.0006);
        setPose(new GamePose(0, 0, 15, 0));
        getDrawable().show();
    }

    public void attackedByEnemy() {

    }

    public void die() {
        Engine.getInstance().getSoundPlayer().deathSound();
        trySleep(75);
        for (int i = 0; i < 3; i++) {
            getDrawable().hide();
            Engine.getInstance().render();
            trySleep(75);
            getDrawable().show();
            Engine.getInstance().render();
            trySleep(75);
        }
        Engine.getInstance().resetLevel();
        health = MAX_HEALTH;
        fuelCount = 0;
    }

    private void trySleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(double dt) {
        super.update(dt);
        if (getPose().z < MIN_Z) {
            die();
        }
        if (this.health <= 0) {
            die();
        }
        Screen.getInstance().writeText("player health", "Health: " + health, Screen.DEFAULT_TEXT_COLOR, 10, 60);
        Screen.getInstance().writeText("player fuel", "Bonfire fuel: " + fuelCount + "/" + Engine.getInstance().getCurrentLevel().getBonfireFuels().size(), Screen.DEFAULT_TEXT_COLOR, 10, 90);
    }

    public int getFuelCount() {
        return fuelCount;
    }


    @Override
    public void pickUpFuel() {
        fuelCount++;
        System.out.println(fuelCount);
    }

    public int getHealth() {
        return health;
    }

    @Override
    public void onSoftCollision(GameObject other, GamePose pose) {
        other.hitByPlayer();
    }

    @Override
    public void hitByDrone() {
        health--;
    }

    @Override
    public void hitByLethalEnemy() {
        die();
    }

    @Override
    public void onHitByBonfire() {

        if (this.fuelCount == Engine.getInstance().getCurrentLevel().getNumBonfireFuels()) {
            Engine.getInstance().incrementLevel();
        }
    }

    @Override
    public void doPower() {
        if (Math.random() > RANDOM_WATER)
            Engine.getInstance().addGameObject(new Water(getPose().translate(new GamePose(0, 5, 2).rotateYaw(pose.yaw())).setYaw(0), WATER_LAUNCH_VEL.rotateYaw(pose.yaw())));
    }

    @Override
    public void reset() {
        fuelCount = 0;
    }

}
