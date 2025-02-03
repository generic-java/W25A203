package org.csse220;

import org.csse220.game_engine.GameObject;
import org.csse220.game_engine.graphics.Drawable;
import org.csse220.game_engine.kinematics.Collideable;
import org.csse220.game_engine.math_utils.Vector3d;

public abstract class GamePlayer extends GameObject {
    private Vector3d position;
    private double yaw;
    private double xVel;
    private double yVel;
    private double zVel;

    public GamePlayer(Collideable collideable, Drawable drawable) {
        super(collideable, drawable);
    }

    public void setXVel(double xVel) {
        this.xVel = xVel;
    }

    public void setYVel(double yVel) {
        this.yVel = yVel;
    }

    public void setZVel(double zVel) {
        this.zVel = zVel;
    }

    public void accelerateZ(double dv) {
        zVel += dv;
    }

    public void move(double dt) {
        incrementX(xVel * dt);
        incrementY(yVel * dt);
        incrementZ(zVel * dt);
    }
}
