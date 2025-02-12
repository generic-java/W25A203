package org.csse220.game_engine.characters;

import org.csse220.game_engine.math_utils.GamePose;

import java.util.ArrayList;
import java.util.Random;

public class DroneManager {
   private int numberOfDrones = (int) (Math.random() *10);
   private ArrayList<Drone> drones = new ArrayList<>();

    public DroneManager(Drone drone) {
        for (int i = 0; i < numberOfDrones; i++) {
            drones.add(drone);
        }
    }

}
