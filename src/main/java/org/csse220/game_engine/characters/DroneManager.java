package org.csse220.game_engine.characters;

import org.csse220.game_engine.ElapsedTime;
import org.csse220.game_engine.math_utils.GamePose;

import java.util.ArrayList;
import java.util.Random;

public class DroneManager {
    private int maxDrones = 10; // Maximum number of drones to spawn
    private ArrayList<Drone> drones = new ArrayList<>();
    private final ElapsedTime elapsedTime;
    private static final long SPAWN_INTERVAL = 30000; // 30 seconds

    public DroneManager() {
        this.elapsedTime = new ElapsedTime();
    }

    public void update() {
        if (elapsedTime.getElapsedTime() >= SPAWN_INTERVAL) {
            spawnDrones();
            elapsedTime.reset(); // resets the timer after spawning
        }
    }

    private void spawnDrones() {
        drones.clear();
        int numberOfDrones = (int) (Math.random() * maxDrones); // random number of drones to spawn
        Random rand = new Random();

        for (int i = 0; i < numberOfDrones; i++) {
            double randomY = rand.nextDouble() * 100;
            GamePose spawnPose = new GamePose(-264, randomY, 50, 0);
            Drone newDrone = new Drone(spawnPose);
            drones.add(newDrone);
        }
    }

    public ArrayList<Drone> getDrones() {
        return drones;
    }
}
