package org.csse220.game_engine.characters;

import org.csse220.game_engine.graphics.Cuboid;
import org.csse220.game_engine.graphics.Point3d;
import org.csse220.game_engine.kinematics.Hitbox;
import org.csse220.game_engine.math_utils.GamePose;

import java.awt.*;

public class PathEnemy extends Enemy {
    private final double SPEED = 0.25;
    private final GamePose startPose;
    private final GamePose endPose;
    private GamePose nextVel;

    public PathEnemy(GamePose startPose, GamePose endPose) {
        super(startPose, new Hitbox(startPose, 5, 5, 5), new Cuboid(startPose, new Point3d(0, 0, 0), 5, 5, 5, Color.magenta));
        this.startPose = startPose;
        this.endPose = endPose;
        setVel(endPose.relativeTo(startPose).normalize().scale(SPEED));
        nextVel = velocity().scale(-1);
    }

    @Override
    public void update() {
        if (true)
            return;
        if (getPose().between(startPose, endPose)) {
            setVel(nextVel);
            nextVel = velocity().scale(-1);
        } else if (pose.between(endPose, startPose)) {
            setVel(nextVel);
            nextVel = velocity().scale(-1);
        }
    }
}
