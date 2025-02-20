package org.csse220.game_engine.characters;

import org.csse220.game_engine.kinematics.Hitbox;
import org.csse220.game_engine.math.GamePose;

public class PathEnemy extends LethalEnemy {
    private static final double SPEED = 0.025;
    private final GamePose startPose;
    private final GamePose endPose;
    private GamePose nextVel;

    public PathEnemy(GamePose startPose, GamePose endPose) {
        super(startPose, new Hitbox(startPose, 5, 15, 5), new Elephant(startPose));


        this.startPose = startPose;
        this.endPose = endPose;
        setVel(endPose.relativeTo(startPose).normalize().scale(SPEED));
        nextVel = velocity().scale(-1);
    }

    @Override
    public void update(double dt) {
        if (!getPose().between(startPose, endPose)) {
            if (pose.distanceTo(startPose) <= pose.distanceTo(endPose)) {
                setPose(startPose);
            } else {
                setPose(endPose);
            }
            setVel(nextVel);
            nextVel = velocity().scale(-1);
        }
        setPose(pose.setYaw(velocity().angle - Math.PI / 2));
    }
}
