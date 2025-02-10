package org.csse220.game_engine.Enemy;

import org.csse220.game_engine.GameObject;
import org.csse220.game_engine.graphics.Drawable;
import org.csse220.game_engine.kinematics.Collideable;
import org.csse220.game_engine.math_utils.Pose3d;
import java.util.Random;

public class Drone extends GameObject {
    private final Pose3d velocity;
    private boolean isActive;

    public Drone(Collideable collideable, Drawable drawable, double screenWidth, double screenHeight) {
        super(collideable, drawable, RandomSpawn(screenWidth, screenHeight));
        this.velocity = new Pose3d(-0.05, 0, 0, 0, 0, 0); // moves left
        this.isActive = true; // if the drone is on the screen or not (should implement collisions)
    }

    private static Pose3d RandomSpawn(double screenWidth, double screenHeight) {
        Random rand = new Random();
        double randomY = rand.nextDouble() * screenHeight; // random y position
        return new Pose3d(screenWidth, randomY, 0, 0, 0, 0); // enters the screen from the right edge
    }

    public void update() {
        if (!isActive) return;
        setPose(getPose().addTo(velocity)); // Moves drone left
    }

    @Override
    public void onCollide() {
        isActive = false; // if drone is Hit disappear

    }

    public boolean isActive() {
        return isActive;
    }

    @Override
    public boolean onCollide(Pose3d collisionDirection) { // not sure what exactly this does.
        return false;
    }
}

