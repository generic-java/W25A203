package org.csse220.game_engine.kinematics;

import org.csse220.game_engine.GameElement;
import org.csse220.game_engine.math_utils.Pose3d;

import java.util.Set;

public abstract class Collideable extends GameElement {

    public Collideable(Pose3d pose, double gravity) {
        super(pose, gravity);
    }

    public Collideable(Pose3d pose) {
        super(pose);
    }

    public Collideable() {
        super(new Pose3d());
    }

    final boolean hasCollided(Collideable other) {
        for (Hitbox firstHitbox : getHitboxes()) {
            for (Hitbox secondHitbox : other.getHitboxes()) {
                if (firstHitbox.intersects(secondHitbox)) {
                    return true;
                }
            }
        }
        return false;
    }

    public abstract Set<Hitbox> getHitboxes();

    /**
     * This method runs at least once when a Collideable collides with another Collideable.
     * If true is returned, the method will run until the Collideable is no longer intersecting with another Collideable.
     */
    public abstract boolean onCollide(Pose3d collisionDirection);
}
