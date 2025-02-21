package org.csse220.game_engine;

/**
 * A simple class that tracks how much time has passed.
 */
public class ElapsedTime {
    private double timestamp;

    public ElapsedTime() {
        reset();
    }

    public double getElapsedTime() {
        return System.currentTimeMillis() - timestamp;
    }

    public double getAndReset() {
        double elapsedTime = getElapsedTime();
        reset();
        return elapsedTime;
    }

    public void reset() {
        timestamp = System.currentTimeMillis();
    }
}
