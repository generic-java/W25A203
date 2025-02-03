package org.csse220.game_engine.kinematics;

public class EmptyHitbox extends Hitbox {
    private static EmptyHitbox instance = null;

    private EmptyHitbox() {
        super(0, 0, 0, 0, 0, 0);
    }

    @Override
    public boolean intersectsWith(Hitbox other) {
        return false;
    }

    public static EmptyHitbox getInstance() {
        if (instance == null) {
            instance = new EmptyHitbox();
        }
        return instance;
    }
}
