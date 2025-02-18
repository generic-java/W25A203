package org.csse220.game_engine;

import org.csse220.game_engine.characters.GamePlayer;
import org.csse220.game_engine.graphics.Screen;
import org.csse220.game_engine.kinematics.Kinematics;
import org.csse220.game_engine.math_utils.GamePose;
import org.csse220.levels.Level;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Engine {
    private static Engine instance = null;

    private Kinematics kinematics;
    private final ArrayList<Level> levels;
    private int levelNumber = -1;
    private GamePlayer player = null;
    private final SoundPlayer soundPlayer;


    private Engine() {
        soundPlayer = new SoundPlayer();
        soundPlayer.startBackground();
        levels = new ArrayList<>();
    }

    public SoundPlayer getSoundPlayer() {
        return soundPlayer;
    }

    public void addLevel(Level level) {
        levels.add(level);
    }

    public void setLevel(int levelNumber) {
        if (levelNumber < levels.size()) {
            this.levelNumber = levelNumber;
            kinematics.clearAllGameObjects();
            addLevelData(levels.get(levelNumber));
            player.setZVel(0);
            player.reset();
            player.setPose(levels.get(levelNumber).getPlayerStartPose());
        }

    }

    public void init(GamePlayer player, JFrame window) {
        this.player = player;
        GameKeyListener keyListener = new GameKeyListener();

        kinematics = new Kinematics(player, keyListener);

        window.add(Screen.getInstance());
        window.addKeyListener(keyListener);
        window.setVisible(true);
    }

    public void startKinematics() {
        kinematics.start();
    }

    private void addLevelData(Level level) {
        for (GameObject gameObject : level.getEnemies()) {
            addGameObject(gameObject);
        }

        for (GameObject gameObject : level.getPlatforms()) {
            addGameObject(gameObject);
        }

        for (GameObject gameObject : level.getBonfireFuels()) {
            addGameObject(gameObject);
            gameObject.reset();
        }

        addGameObject(level.getBonfire());
    }

    public void resetLevel() {

        setLevel(this.levelNumber);
    }

    public void incrementLevel() {
        if (levelNumber != levels.size()) {
            levelNumber++;
            setLevel(levelNumber);
        }
    }

    public void render() {
        kinematics.render(0.01);
    }

    private void kill() {
        kinematics.kill();
    }

    public void addGameObject(GameObject gameObject) {
        kinematics.addGameObject(gameObject);
    }

    public void removeGameObject(GameObject gameObject) {
        kinematics.removeGameObject(gameObject);
    }

    public GamePlayer getPlayer() {
        return player;
    }

    public GamePose getPlayerPosition() {
        return player.getPose();
    }

    public int getLevelNumber() {
        return levelNumber;
    }

    public Level getCurrentLevel() {
        return levels.get(levelNumber);
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

    public void addEventManager(Runnable runnable) {
        kinematics.addEventManager(runnable);
    }


    public void setBackground(Color color) {
        kinematics.setBackground(color);
    }
}
