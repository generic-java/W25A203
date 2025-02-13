package org.csse220.game_engine;

import org.csse220.game_engine.characters.GamePlayer;
import org.csse220.game_engine.graphics.Screen;
import org.csse220.game_engine.kinematics.Kinematics;
import org.csse220.game_engine.math_utils.GamePose;
import org.csse220.levels.Level;

import javax.swing.*;
import java.util.ArrayList;

public class Engine {
    private static Engine instance = null;

    private Kinematics kinematics;
    private final ArrayList<Level> levels;
    private int levelNumber = -1;
    private GamePlayer player = null;

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
            addLevelData(levels.get(levelNumber));
        }
    }

    public void init(GamePlayer player, JFrame window) {
        this.player = player;
        GameKeyListener keyListener = new GameKeyListener();

        kinematics = new Kinematics(player, keyListener);

        window.add(Screen.getInstance());
        window.addKeyListener(keyListener);
        window.setVisible(true);

        //renderer.start();
        kinematics.start();
    }

    private void addLevelData(Level level) {
        for (GameObject gameObject : level.getEnemies()) {
            addGameObject(gameObject);
            System.out.println(gameObject.getPose());
        }

        for (GameObject gameObject : level.getPlatforms()) {
            addGameObject(gameObject);
        }
    }

    public void resetLevel() {
        setLevel(this.levelNumber);
    }

    private void kill() {
        kinematics.kill();
    }

    public void addGameObject(GameObject gameObject) {
        kinematics.addGameObject(gameObject);
    }

    public GamePlayer getPlayer() {
        return player;
    }

    public GamePose getPlayerPosition() {
        return player.getPose();
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
