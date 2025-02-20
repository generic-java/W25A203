package org.csse220.game_engine.characters;

import org.csse220.game_engine.ElapsedTime;
import org.csse220.game_engine.Engine;
import org.csse220.game_engine.math.GamePose;

import java.util.ArrayList;
import java.util.Random;

public class PaperAirplaneManager implements Runnable {
    private static final int MAX_AIRPLANES = 3; // Maximum number of drones to spawn
    private final ArrayList<PaperAirplane> paperAirplanes = new ArrayList<>();
    private final ElapsedTime elapsedTime;
    private static final long SPAWN_INTERVAL = 5000; // 30 seconds

    public PaperAirplaneManager() {
        this.elapsedTime = new ElapsedTime();
    }

    public void update() {
        if (elapsedTime.getElapsedTime() >= SPAWN_INTERVAL) {
            spawnDrones();
            elapsedTime.reset(); // resets the timer after spawning
        }
    }

    private void spawnDrones() {
        int numberOfDrones = (int) (Math.random() * MAX_AIRPLANES); // random number of drones to spawn
        Random rand = new Random();
        GamePose playerPose = Engine.getInstance().getPlayerPosition();

        for (int i = 0; i < numberOfDrones; i++) {
            double randomX = (rand.nextDouble() - 0.5) * 600;
            double randomY = rand.nextDouble() * 100 + 200;
            GamePose spawnPose = new GamePose(randomX, randomY, 0, 0).rotateYaw(playerPose.yaw()).addTo(playerPose).setYaw(0);
            Engine.getInstance().addGameObject(new PaperAirplane(spawnPose));
        }
    }

    @Override
    public void run() {
        update();
    }

    public ArrayList<PaperAirplane> getDrones() {
        return paperAirplanes;
    }
}
