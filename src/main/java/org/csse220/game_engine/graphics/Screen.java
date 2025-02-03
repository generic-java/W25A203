package org.csse220.game_engine.graphics;

import org.csse220.Main;

import javax.swing.*;
import java.awt.*;

public class Screen extends JComponent {
    private static Screen instance = null;
    private final int PIXEL_SIZE = 4; // How pixelated the screen should appear.  Shouldn't be less than 1
    public final Color[][] pixels;
    private final Color[][] buffer;
    private final double squareScreenWidth;

    public double getSquareScreenWidth() {
        return squareScreenWidth;
    }

    private Screen() { // This currently assumes that the game will be in full screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        squareScreenWidth = screenSize.getWidth() / (double) PIXEL_SIZE;
        pixels = new Color[screenSize.height / PIXEL_SIZE][screenSize.width / PIXEL_SIZE]; // Effectively a floor() call as these are implicitly cast to integers
        for (int i = 0; i < pixels.length; i++) {
            for (int j = 0; j < pixels[0].length; j++) {
                pixels[i][j] = Color.WHITE;
            }
        }
        buffer = new Color[pixels.length][pixels[0].length];
    }

    public void fill(Color color) {
        synchronized (pixels) {
            for (int i = 0; i < pixels.length; i++) {
                for (int j = 0; j < pixels[0].length; j++) {
                    pixels[i][j] = color;
                }
            }
        }
    }

    public synchronized void refresh() {
        synchronized (buffer) {
            for (int i = 0; i < pixels.length; i++) {
                System.arraycopy(pixels[i], 0, buffer[i], 0, pixels[0].length);
            }
        }
        repaint();
    }

    public void paintPixel(int x, int y, Color color) {
        if (y < pixels.length && y >= 0 && x < pixels[0].length && x >= 0) {
            pixels[y][x] = color;
        }
    }

    protected int getPixelSize() {
        return PIXEL_SIZE;
    }

    public Dimension getSize() {
        Dimension absoluteSize = getBounds().getSize();
        return new Dimension((int) (absoluteSize.getWidth() / PIXEL_SIZE), (int) (absoluteSize.getHeight() / PIXEL_SIZE));
    }

    public int getAdjustedWidth() {
        return pixels[0].length;
    }

    public int getAdjustedHeight() {
        return pixels.length;
    }

    /**
     * This method is called periodically as a result of an upstream call to repaint().
     * It redraws every pixel on the screen.
     *
     * @param g the <code>Graphics</code> object to protect
     */
    @Override
    protected void paintComponent(Graphics g) {
        synchronized (buffer) {
            super.paintComponent(g);
            for (int i = 0; i < buffer.length; i++) {
                for (int j = 0; j < buffer[0].length; j++) {
                    g.setColor(buffer[i][j]);
                    g.fillRect(j * PIXEL_SIZE, i * PIXEL_SIZE, PIXEL_SIZE, PIXEL_SIZE);
                }
            }
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(Color.BLACK);
            g2.drawString("fps: " + Main.FPS, 70, 20);
        }
    }

    public static Screen getInstance() {
        if (instance == null) {
            instance = new Screen();
        }
        return instance;
    }
}
