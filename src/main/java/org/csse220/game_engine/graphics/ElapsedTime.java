package org.csse220.game_engine.graphics;

public class ElapsedTime {
    private long timestamp;

    public ElapsedTime() {
        reset();
    }

    public long elapsedTime() {
        return System.currentTimeMillis() - timestamp;
    }

    public void reset() {
        timestamp = System.currentTimeMillis();
    }
}
