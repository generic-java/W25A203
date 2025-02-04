package org.csse220.game_engine.kinematics;

import org.csse220.game_engine.GameElement;

import java.util.Set;

public abstract class Collideable extends GameElement {

    public final boolean hasCollided(Collideable other) {
        for (Hitbox hitbox : getHitboxes()) {
            for (Hitbox otherHitbox : other.getHitboxes()) {
                if (hitbox.intersectsWith(otherHitbox)) {
                    return true;
                }
            }
        }
        return false;
    }

    abstract Set<Hitbox> getHitboxes();
}
