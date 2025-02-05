package org.csse220.game_engine;

import org.csse220.game_engine.graphics.Drawable;
import org.csse220.game_engine.graphics.Renderer;
import org.csse220.game_engine.graphics.Screen;
import org.csse220.game_engine.kinematics.Collideable;
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

    public void addGameElement(GameElement element) {
        kinematics.addGameElement(element);
        if (element instanceof Drawable) {
            addDrawable((Drawable) element);
        }
    }

    public void removeGameElement(GameElement element) {
        kinematics.removeGameElement(element);
        if (element instanceof Drawable) {
            removeDrawable((Drawable) element);
        }
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

    public static Engine getInstance() {
        if (instance == null) {
            instance = new Engine();
        }
        return instance;
    }
}
