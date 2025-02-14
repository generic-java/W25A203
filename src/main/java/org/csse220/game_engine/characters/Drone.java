package org.csse220.game_engine.characters;

import org.csse220.game_engine.Engine;
import org.csse220.game_engine.graphics.CompoundDrawable;
import org.csse220.game_engine.graphics.Face;
import org.csse220.game_engine.graphics.Point3d;
import org.csse220.game_engine.math_utils.GamePose;

import java.awt.*;

public class Drone extends Enemy {
    private static final double MOVE_SPEED = .0025;
    //  private static final double MIN_DISTANCE = 2.0
    private boolean isActive;

    public Drone(GamePose pose) {
        super(pose, null,
                new CompoundDrawable(
                        pose,
                        new Face(
                                new Point3d(pose.x(), pose.y(), pose.z()),
                                new Point3d(pose.x() + 3, pose.y() - 0.5, pose.z() + 1.5),
                                new Point3d(pose.x(), pose.y() + 8, pose.z()),
                                Color.WHITE
                        ),
                        new Face(
                                new Point3d(pose.x(), pose.y(), pose.z()),
                                new Point3d(pose.x() - 3, pose.y() - 0.5, pose.z() + 1.5),
                                new Point3d(pose.x(), pose.y() + 8, pose.z()),
                                Color.WHITE
                        ),
                        new Face(
                                new Point3d(pose.x(), pose.y(), pose.z()),
                                new Point3d(pose.x(), pose.y() - 0.5, pose.z() - 2),
                                new Point3d(pose.x(), pose.y() + 8, pose.z()),
                                Color.WHITE
                        )
                ));
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
        // if (isActive) {
        GamePose playerPos = Engine.getInstance().getPlayerPosition();
        GamePose proportionalDistance = playerPos.relativeTo(pose);
        GamePose velocity = proportionalDistance.scale(MOVE_SPEED);

        setPose(getPose().addTo(velocity));
        //   }
    }

    public boolean isActive() {

        return isActive;
    }
}
