package org.csse220.game_engine.graphics;

import org.csse220.game_engine.math_utils.GamePose;
import org.csse220.game_engine.math_utils.Vector3d;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CompoundDrawable extends Drawable {
    private final Map<Drawable, GamePose> drawables = new HashMap<>();

    public CompoundDrawable(GamePose pose, Drawable... drawables) {
        super(pose, null);
        Arrays.stream(drawables).forEach((drawable) -> this.drawables.put(drawable, drawable.getPose().relativeTo(pose)));
        setPose(pose);
    }

    @Override
    public void setPose(GamePose pose) {
        super.setPose(pose);
        drawables.forEach((drawable, relativePose) -> drawable.setPose(relativePose.addTo(pose)));
    }

    @Override
    public void hide() {
        for (Drawable drawable : drawables.keySet()) {
            drawable.hide();
        }
    }

    @Override
    public void show() {
        for (Drawable drawable : drawables.keySet()) {
            drawable.show();
        }
    }

    @Override
    public void draw(Vector3d camPose, double pitch, double yaw, boolean shade) {
        drawables.keySet().forEach((drawable) -> drawable.draw(camPose, pitch, yaw, shade));
    }

}