package org.csse220.game_engine.characters;

import org.csse220.game_engine.GameObject;
import org.csse220.game_engine.graphics.Cuboid;
import org.csse220.game_engine.graphics.Point3d;
import org.csse220.game_engine.kinematics.Hitbox;
import org.csse220.game_engine.math_utils.GamePose;

import java.awt.*;

public class Drone extends Enemy {
    private static double MOVE_SPEED = 0.25;
    private static final double kP = 0.5;
    private boolean isActive;

    public Drone(GamePose pose) {
        super(pose, new Hitbox(pose, 5, 5, 5), new Cuboid(pose, new Point3d(0, 0, 0), 5, 5, 5, Color.YELLOW));
        //this.velocity = new GamePose(-0.05, 0, 0, 0, 0, 0); // moves left
        this.isActive = true; // if the drone is on the screen or not (should implement collisions)
    }

//    private static GamePose RandomSpawn() {
//        Random rand = new Random();
//        double randomY = rand.nextDouble(); // random y position
//        return new GamePose(, randomY, 50, 0, 0, 0); // enters the screen from the right edge
//    }

    @Override
    public void update() {
        if (isActive) {

        }

    }

    @Override
    public void onCollide(GameObject other, GamePose moveDirection) {
        super.onCollide(other, moveDirection);
        isActive = false; // if drone is Hit disappear

    }

    public boolean isActive() {
        return isActive;
    }
}

