package org.csse220.game_engine;

import org.csse220.game_engine.graphics.Drawable;
import org.csse220.game_engine.graphics.Renderer;
import org.csse220.game_engine.graphics.Screen;
import org.csse220.game_engine.kinematics.Collideable;
import org.csse220.game_engine.kinematics.Kinematics;

import javax.swing.*;

public class EngineMain {
    private static boolean instanceMade = false;

    private final JFrame window;
    private final Renderer renderer;
    private final Kinematics kinematics;

    public EngineMain(GamePlayer player, JFrame window) {
        if (instanceMade)
            throw new RuntimeException("Only one instance of GameEngine can be created.");
        instanceMade = true;
        GameKeyListener keyListener = new GameKeyListener();

        renderer = new Renderer();
        kinematics = new Kinematics(player, keyListener);

        this.window = window;
        window.add(Screen.getInstance());
        window.addKeyListener(keyListener);
    }

    public void start() {
        window.setVisible(true);
        renderer.start();
        kinematics.start();
    }

    private void kill() {
        renderer.kill();
        kinematics.kill();
    }

    public void loadLevel() {
        // TODO: finish this method
    }

    public void addGameObject(GameObject object) {
        renderer.addDrawable(object.getDrawable());
        kinematics.addCollideable(object.getCollideable());
    }

    public void removeGameObject(GameObject object) {
        renderer.removeDrawable(object.getDrawable());
        kinematics.removeCollideable(object.getCollideable());
    }

    public void addCollideable(Collideable collideable) {
        kinematics.addCollideable(collideable);
    }

    public void removeCollideable(Collideable collideable) {
        kinematics.removeCollideable(collideable);
    }

    public void addDrawable(Drawable drawable) {
        renderer.addDrawable(drawable);
    }

    public void removeDrawable(Drawable drawable) {
        renderer.removeDrawable(drawable);
    }

    public boolean keyPressed(int keycode) {
        return kinematics.keyPressed(keycode);
    }
}
