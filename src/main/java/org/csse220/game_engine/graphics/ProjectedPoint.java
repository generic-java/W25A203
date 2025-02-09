package org.csse220.game_engine.graphics;

import java.awt.*;

public class ProjectedPoint {
    private final int screenX;
    private final int screenY;

    public ProjectedPoint(double x, double y) {
        Dimension size = Screen.getInstance().getSize();
        this.screenX = (int) (size.getWidth() / 2 + x);
        this.screenY = (int) (size.getHeight() / 2 - y);
    }

    public static double xToActual(double screenX) {
        Dimension size = Screen.getInstance().getSize();
        return screenX - size.getWidth() / 2;
    }

    public static double yToActual(double screenY) {
        Dimension size = Screen.getInstance().getSize();
        return size.getHeight() / 2 - screenY;
    }

    public int x() {
        return screenX;
    }

    public int y() {
        return screenY;
    }

    @Override
    public String toString() {
        return String.format("x: %s y: %s", screenX, screenY);
    }
}