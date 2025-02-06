package org.csse220.game_engine;

import org.csse220.game_engine.graphics.Drawable;
import org.csse220.game_engine.graphics.Renderer;
import org.csse220.game_engine.graphics.Screen;
import org.csse220.game_engine.kinematics.Kinematics;
import org.csse220.levels.Level;

import javax.swing.*;

public class Engine {
    private static Engine instance = null;

    private Renderer renderer;
    private Kinematics kinematics;

    private Engine() {

    }

    public void init(GameObject player, JFrame window) {
        GameKeyListener keyListener = new GameKeyListener();

        renderer = new Renderer();
        renderer.addDrawable(player.getDrawable());
        kinematics = new Kinematics(player, keyListener);

        window.add(Screen.getInstance());
        window.addKeyListener(keyListener);
        window.setVisible(true);

        renderer.start();
        kinematics.start();
    }

    private void kill() {
        renderer.kill();
        kinematics.kill();
    }

    public void loadLevel(Level level) {
        // TODO: finish this method
    }

    public void addGameObject(GameObject gameObject) {
        kinematics.addGameObject(gameObject);
        if (gameObject.hasDrawable()) {
            renderer.addDrawable(gameObject.getDrawable());
        }
    }

//    public void removeGameObject(GameObject gameObject) {
//        gameObjects.remove(gameObject);
//        if (gameObject.hasCollideable()) {
//
//        }
//        if (gameObject.hasDrawable()) {
//
//        }
//    }

    public void addDrawable(Drawable drawable) {
        renderer.addDrawable(drawable);
    }

    public void removeDrawable(Drawable drawable) {
        renderer.removeDrawable(drawable);
    }

    public boolean keyPressed(int keycode) {
        return kinematics.keyPressed(keycode);
    }

    public static Engine getInstance() {
        if (instance == null) {
            instance = new Engine();
        }
        return instance;
    }


}
