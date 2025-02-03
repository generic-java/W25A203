package org.csse220.game_engine.graphics;

import org.csse220.game_engine.KillableThread;
import org.csse220.game_engine.math_utils.Pose3d;
import org.csse220.game_engine.math_utils.Vector3d;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class Renderer extends KillableThread {
    private final Set<Drawable> drawables;

    public Renderer() {
        this.drawables = new HashSet<>();
    }

    public void addDrawable(Drawable drawable) {
        drawables.add(drawable);
    }

    public void removeDrawable(Drawable drawable) {
        drawables.remove(drawable);
    }

    @Override
    public void start() {
        super.start();
        activate();
    }

    @Override
    public void run() {
        while (isActive()) {
            Camera camera = Camera.getInstance();
            Pose3d camPose = camera.getPosition();
            Vector3d.updatePitchYaw(camPose.pitch(), camPose.yaw());
            Screen.getInstance().fill(Color.WHITE);
            for (Drawable drawable : drawables) {
                synchronized (drawable) {
                    drawable.draw(camera.getPosition(), camPose.pitch(), camPose.yaw(), true);
                }
            }
            ZBuffer.getInstance().wipe();
            Screen.getInstance().refresh();
        }
    }
}
