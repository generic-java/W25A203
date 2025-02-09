package org.csse220.game_engine;

import org.csse220.game_engine.graphics.Drawable;
import org.csse220.game_engine.graphics.Screen;
import org.csse220.game_engine.kinematics.Kinematics;
import org.csse220.levels.Level;

import javax.swing.*;
import java.util.ArrayList;

public class Engine {
    private static Engine instance = null;

    private Kinematics kinematics;
    private final ArrayList<Level> levels;
    private int levelNumber = -1;
    private GameObject player = null;

    private Engine() {
        levels = new ArrayList<>();
    }

    public void addLevel(Level level) {
        levels.add(level);
    }

    public void setLevel(int levelNumber) {
        if (this.levelNumber != levelNumber && levelNumber < levels.size()) {
            this.levelNumber = levelNumber;
            kinematics.clearAllGameObjects();
            for (GameObject gameObject : levels.get(levelNumber).getGameObjects()) {
                addGameObject(gameObject);
            }
        }
    }

    public void init(SolidGameObject player, JFrame window) {
        this.player = player;
        GameKeyListener keyListener = new GameKeyListener();

        kinematics = new Kinematics(player, keyListener);

        window.add(Screen.getInstance());
        window.addKeyListener(keyListener);
        window.setVisible(true);

        //renderer.start();
        kinematics.start();
    }

    private void kill() {
        kinematics.kill();
    }

    public void setLevel(Level level) {
        // TODO: finish this method
    }

    public void addGameObject(GameObject gameObject) {
        kinematics.addGameObject(gameObject);
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

    }

    public void removeDrawable(Drawable drawable) {
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
