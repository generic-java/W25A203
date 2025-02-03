package org.csse220.game_engine.kinematics;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class CompoundHitbox extends Collideable {
    private final HashSet<Hitbox> hitboxes = new HashSet<>();

    public CompoundHitbox(Hitbox... hitboxes) {
        this.hitboxes.addAll(Arrays.asList(hitboxes));
    }

    @Override
    public Set<Hitbox> getHitboxes() {
        return hitboxes;
    }
}
