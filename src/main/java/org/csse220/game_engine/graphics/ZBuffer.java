package org.csse220.game_engine.graphics;

public class ZBuffer {
    private static ZBuffer instance = null;
    private final float[][] buffer;

    private ZBuffer() {
        buffer = new float[Screen.getInstance().getAdjustedHeight()][Screen.getInstance().getAdjustedWidth()];
        wipe();
    }

    void set(int x, int y, float depth) {
        if (y >= 0 && y < buffer.length && x >= 0 && x < buffer[0].length) {
            buffer[y][x] = depth;
        }
    }

    void set(int x, int y, double depth) {
        set(x, y, (float) depth);
    }

    double get(int x, int y) {
        if (y >= 0 && y < buffer.length && x >= 0 && x < buffer[0].length) {
            return buffer[y][x];
        }
        return 0;
    }

    void wipe() {
        for (int i = 0; i < buffer.length; i++) {
            for (int j = 0; j < buffer.length; j++) {
                buffer[i][j] = Float.POSITIVE_INFINITY;
            }
        }
    }

    public static ZBuffer getInstance() {
        if (instance == null) {
            instance = new ZBuffer();
        }
        return instance;
    }
}
