package org.csse220.game_engine.graphics;

/**
 * This is used to interpolate the depths of pixels based on their position on the 2D screen
 */
public interface DepthCalculator {
    double calculateDepth(double projectedX, double projectedY);
}
