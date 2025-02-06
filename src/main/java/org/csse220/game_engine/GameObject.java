package org.csse220.game_engine;

import org.csse220.game_engine.graphics.Drawable;
import org.csse220.game_engine.kinematics.Collideable;
import org.csse220.game_engine.kinematics.Hitbox;
import org.csse220.game_engine.math_utils.Pose3d;
import org.csse220.game_engine.math_utils.Vector3d;

import java.util.Set;

public abstract class GameObject extends Collideable implements Drawable {
    private final Collideable collideable;
    private final Drawable drawable;
    private double yaw;

    public GameObject(Collideable collideable, Drawable drawable, Pose3d initialPose) {
        this.collideable = collideable;
        this.drawable = drawable;
        setPose(initialPose);
    }

    public GameObject(Collideable collideable, Drawable drawable) {
        this(collideable, drawable, new Pose3d());
    }

    public final Collideable getCollideable() {
        return collideable;
    }

    public final Drawable getDrawable() {
        return drawable;
    }

    public double yaw() {
        return yaw;
    }

    public void incrementYaw(double increment) {
        yaw += increment;
    }

    abstract public void onCollide();

    @Override
    public Set<Hitbox> getHitboxes() {
        return collideable.getHitboxes();
    }

    @Override
    public void draw(Vector3d camPose, double pitch, double yaw, boolean shade) {
        drawable.draw(camPose, pitch, yaw, shade);
    }
}
