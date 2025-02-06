package org.csse220.game_engine;

import org.csse220.game_engine.graphics.Drawable;
import org.csse220.game_engine.kinematics.Collideable;
import org.csse220.game_engine.math_utils.GamePose;

public class GameObject extends GameElement {
    private final Collideable collideable;
    private final Drawable drawable;
    private final GamePose relativeCollideablePose;
    private final GamePose relativeDrawablePose;
    private double yaw;

    public GameObject(GamePose pose, Collideable collideable, Drawable drawable) {
        this.collideable = collideable;
        this.drawable = drawable;
        if (hasCollideable())
            relativeCollideablePose = collideable.getPose().relativeTo(pose);
        else
            relativeCollideablePose = new GamePose();
        if (hasDrawable())
            relativeDrawablePose = drawable.getPose().relativeTo(pose);
        else
            relativeDrawablePose = new GamePose();
    }

    public final boolean hasCollideable() {
        return collideable != null;
    }

    public final boolean hasDrawable() {
        return drawable != null;
    }

    @Override
    public void move(GamePose moveDirection, double dt) {
        super.move(moveDirection, dt);
        if (hasCollideable())
            collideable.setPose(relativeCollideablePose.addTo(pose));
        if (hasDrawable())
            drawable.setPose(relativeDrawablePose.addTo(pose));
    }

    public double yaw() {
        return yaw;
    }

    public void incrementYaw(double increment) {
        yaw += increment;
    }

    public Collideable getCollideable() {
        return collideable;
    }

    public Drawable getDrawable() {
        return drawable;
    }

    //protected abstract boolean onCollide();
}
