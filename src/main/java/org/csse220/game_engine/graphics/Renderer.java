package org.csse220.game_engine.graphics;

import org.csse220.game_engine.KillableThread;
import org.csse220.game_engine.math_utils.CameraPose;
import org.csse220.game_engine.math_utils.Vector3d;

import java.awt.*;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class Renderer extends KillableThread {
    private final Set<Drawable> drawables;

    public Renderer() {
        this.drawables = ConcurrentHashMap.newKeySet();
    }

    public void addDrawable(Drawable drawable) {
        drawables.add(drawable);
    }

    public void removeDrawable(Drawable drawable) {
        drawables.remove(drawable);
    }

    public void clearAllDrawables() {
        drawables.clear();
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
            CameraPose camPose = camera.getPose();
            Vector3d.updatePitchYaw(camPose.pitch(), camPose.yaw());
            Screen.getInstance().fill(Color.WHITE);
            for (Drawable drawable : drawables) {
                synchronized (drawable) {
                    drawable.draw(camera.getPose(), camPose.pitch(), camPose.yaw(), true);
                }
            }
            ZBuffer.getInstance().wipe();
            Screen.getInstance().refresh();
        }
    }
}
