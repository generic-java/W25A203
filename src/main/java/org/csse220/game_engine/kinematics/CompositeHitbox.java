package org.csse220.game_engine.kinematics;

import org.csse220.game_engine.math.GamePose;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CompositeHitbox extends Collideable {
    private final Map<Hitbox, GamePose> hitboxes = new HashMap<>();

    public CompositeHitbox(GamePose pose, Hitbox... hitboxes) {
        super(pose);
        for (Hitbox hitbox : hitboxes) {
            this.hitboxes.put(hitbox, hitbox.getPose().relativeTo(pose));
        }
    }

    @Override
    public void setPose(GamePose pose) {
        super.setPose(pose);
        for (Hitbox hitbox : hitboxes.keySet()) {
            hitbox.setPose(pose.addTo(hitboxes.get(hitbox))); // Add the relative pose of the hitbox to the overall pose
        }
    }

    @Override
    public Set<Hitbox> getHitboxes() {
        return hitboxes.keySet();
    }

//    @Override
//    public boolean onCollide(GamePose collisionDirection) {
//        return false;
//    }
}
