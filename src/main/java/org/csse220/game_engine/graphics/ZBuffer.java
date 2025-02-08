package org.csse220.game_engine.graphics;

public class ZBuffer {
    private static ZBuffer instance = null;
    private final double[][][] buffer;

    private ZBuffer() {
        buffer = new double[Screen.getInstance().getAdjustedHeight()][Screen.getInstance().getAdjustedWidth()][2];
        wipe();
    }

    void set(int x, int y, double depth, double angle) {
        if (y >= 0 && y < buffer.length && x >= 0 && x < buffer[0].length) {
            buffer[y][x] = new double[]{depth, angle};
        }
    }

    double[] get(int x, int y) {
        if (y >= 0 && y < buffer.length && x >= 0 && x < buffer[0].length) {
            return buffer[y][x];
        }
        return new double[]{0, 0};
    }

    public void wipe() {
        for (int i = 0; i < buffer.length; i++) {
            for (int j = 0; j < buffer.length; j++) {
                buffer[i][j] = new double[]{Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY};
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
