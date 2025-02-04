package org.csse220.game_engine.kinematics;

import org.csse220.game_engine.GameObject;
import org.csse220.game_engine.graphics.Drawable;
import org.csse220.game_engine.math_utils.Vector3d;

public class MovingObject extends GameObject {

    private Vector3d vel;

    public MovingObject(Collideable collideable, Drawable drawable) {
        super(collideable, drawable);
    }

    public void setVelocity(Vector3d velocity) {
        
    }

    public void move(double dt) {
        getPose().translate(vel.scale(dt));
    }

    @Override
    public void onCollide() {

    }
}
