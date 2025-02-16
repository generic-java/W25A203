package org.csse220.game_engine.characters;

import org.csse220.game_engine.graphics.Cuboid;
import org.csse220.game_engine.kinematics.Hitbox;
import org.csse220.game_engine.math_utils.GamePose;

import java.awt.*;

public class PathEnemy extends Enemy {
    private static final double SPEED = 0.025;
    private final GamePose startPose;
    private final GamePose endPose;
    private GamePose nextVel;

    public PathEnemy(GamePose startPose, GamePose endPose) {
        super(startPose, new Hitbox(startPose, 5, 5, 5), new Cuboid(startPose, new GamePose(), 5, 5, 5, Color.magenta));
        this.startPose = startPose;
        this.endPose = endPose;
        setVel(endPose.relativeTo(startPose).normalize().scale(SPEED));
        nextVel = velocity().scale(-1);
    }

    @Override
    public void update() {
        if (!getPose().between(startPose, endPose)) {
            if (pose.distanceTo(startPose) <= pose.distanceTo(endPose)) {
                setPose(startPose);
            } else {
                setPose(endPose);
            }
            setVel(nextVel);
            nextVel = velocity().scale(-1);

        }
    }

    @Override
    public boolean blocksMovement() {
        return false;
    }
}
