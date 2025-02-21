package org.csse220.game_engine.characters;

import org.csse220.game_engine.ElapsedTime;
import org.csse220.game_engine.Engine;
import org.csse220.game_engine.GameObject;
import org.csse220.game_engine.graphics.CompositeDrawable;
import org.csse220.game_engine.graphics.Face;
import org.csse220.game_engine.graphics.Point3d;
import org.csse220.game_engine.kinematics.Hitbox;
import org.csse220.game_engine.math.GamePose;

import java.awt.*;

/**
 * Whoosh.  This class describes the paper airplane that tracks the player.  It uses a proportional controller for tracking.
 */
public class PaperAirplane extends Enemy {
    private static final double MOVE_KP = 0.0055;
    private static final int MAX_TRACKING_TIME_MILLIS = 5000;
    private static final int MAX_DISPLAY_TIME_MILLIS = 10000;
    private static final GamePose DEATH_TARGET_POSE = new GamePose(0, 0, -100, 0);
    private final ElapsedTime timer;

    public PaperAirplane(GamePose pose) {
        super(pose, new Hitbox(pose, 8, 7, 15),
                new CompositeDrawable(
                        pose,
                        new Face(
                                pose,
                                new Point3d(pose.x(), pose.y(), pose.z()),
                                new Point3d(pose.x() + 3, pose.y() - 0.5, pose.z() + 1.5),
                                new Point3d(pose.x(), pose.y() + 8, pose.z()),
                                Color.WHITE
                        ),
                        new Face(
                                pose,
                                new Point3d(pose.x(), pose.y(), pose.z()),
                                new Point3d(pose.x() - 3, pose.y() - 0.5, pose.z() + 1.5),
                                new Point3d(pose.x(), pose.y() + 8, pose.z()),
                                Color.WHITE
                        ),
                        new Face(
                                pose,
                                new Point3d(pose.x(), pose.y(), pose.z()),
                                new Point3d(pose.x(), pose.y() - 0.5, pose.z() - 2),
                                new Point3d(pose.x(), pose.y() + 8, pose.z()),
                                Color.WHITE
                        )
                ));
        timer = new ElapsedTime();
    }


    @Override
    public void update(double dt) {
        super.update(dt);
        GamePose targetPose = DEATH_TARGET_POSE;
        if (timer.getElapsedTime() < MAX_TRACKING_TIME_MILLIS) {
            targetPose = Engine.getInstance().getPlayerPosition().translateZ(10);
        } else if (timer.getElapsedTime() > MAX_DISPLAY_TIME_MILLIS) {
            Engine.getInstance().removeGameObject(this);
        }
        GamePose error = targetPose.relativeTo(getPose());
        double targetYaw = error.angle - Math.PI / 2;
        setPose(getPose().addTo(error.scale(MOVE_KP)).setYaw(targetYaw));
    }

    @Override
    public boolean blocksMovement() {
        return false;
    }

    @Override
    public void onSoftCollision(GameObject other, GamePose pose) {
        other.hitByDrone();
    }

    @Override
    public void hitByPlayer() {
        Engine.getInstance().removeGameObject(this);
    }

    @Override
    public void hitByWater() {
        hitByPlayer();
    }
}
