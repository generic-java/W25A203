package org.csse220.game_engine.kinematics;

import org.csse220.game_engine.graphics.PlaceableObject;
import org.csse220.game_engine.math_utils.GamePose;

import java.util.Set;

public abstract class Collideable extends PlaceableObject {

    public Collideable(GamePose pose) {
        super(pose);
    }

    public Collideable() {
        super(new GamePose());
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
    //public abstract boolean onCollide(GamePose collisionDirection);
}
