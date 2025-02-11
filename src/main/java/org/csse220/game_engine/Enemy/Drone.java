package org.csse220.game_engine.Enemy;

import org.csse220.game_engine.GameObject;
import org.csse220.game_engine.SolidGameObject;
import org.csse220.game_engine.graphics.Drawable;
import org.csse220.game_engine.kinematics.Collideable;
import org.csse220.game_engine.math_utils.GamePose;

public class Drone extends SolidGameObject {
    private boolean isActive;

    public Drone(GamePose pose, Collideable collideable, Drawable drawable) {
        super(pose, collideable, drawable);
        //this.velocity = new GamePose(-0.05, 0, 0, 0, 0, 0); // moves left
        this.isActive = true; // if the drone is on the screen or not (should implement collisions)
    }

//    private static GamePose RandomSpawn() {
//        Random rand = new Random();
//        double randomY = rand.nextDouble(); // random y position
//        return new GamePose(, randomY, 50, 0, 0, 0); // enters the screen from the right edge
//    }

    @Override
    protected void update() {
        if (!isActive) return;
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

