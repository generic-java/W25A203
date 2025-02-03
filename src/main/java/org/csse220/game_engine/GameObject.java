package org.csse220.game_engine;

import org.csse220.game_engine.graphics.Drawable;
import org.csse220.game_engine.kinematics.Collideable;
import org.csse220.game_engine.math_utils.Vector3d;

public class GameObject {
    private final Collideable collideable;
    private Drawable drawable;

    private Vector3d position = new Vector3d();
    private double yaw;

    public GameObject(Collideable collideable, Drawable drawable) {
        this.collideable = collideable;
    }

    public final Collideable getCollideable() {
        return collideable;
    }

    public final Drawable getDrawable() {
        return drawable;
    }

    public Vector3d getPose() {
        return position;
    }

    public void incrementX(double increment) {
        position = position.translate(increment, 0, 0);
    }

    public void incrementY(double increment) {
        position = position.translate(0, increment, 0);
    }

    public void incrementZ(double increment) {
        position = position.translate(0, 0, increment);
    }

    public void incrementPosition(Vector3d increment) {
        incrementX(increment.x());
        incrementY(increment.y());
        incrementZ(increment.z());
    }

    public double yaw() {
        return yaw;
    }

    public void incrementYaw(double increment) {
        yaw += increment;
    }
}
