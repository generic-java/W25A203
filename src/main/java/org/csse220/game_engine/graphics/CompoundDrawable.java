package org.csse220.game_engine.graphics;

import org.csse220.game_engine.math_utils.GamePose;
import org.csse220.game_engine.math_utils.Vector3d;

import java.awt.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class CompoundDrawable extends Drawable {
    private final Set<Drawable> drawables = new HashSet<>();

    public CompoundDrawable(GamePose pose, Drawable... drawables) {
        super(pose, Color.WHITE);
        this.drawables.addAll(Arrays.asList(drawables));
        setPose(pose);
    }

    @Override
    public void setPose(GamePose pose) {
        drawables.forEach((drawable) -> drawable.setPose(pose));
    }

    @Override
    public void draw(Vector3d camPose, double pitch, double yaw, boolean shade) {
        drawables.forEach((drawable) -> drawable.draw(camPose, pitch, yaw, shade));
    }
}