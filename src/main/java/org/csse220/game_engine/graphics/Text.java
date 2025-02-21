package org.csse220.game_engine.graphics;

import java.awt.*;

/**
 * Describes text that can be drawn using Screen.
 *
 * @param key   A string identifier for the text to be drawn
 * @param value The text to be drawn
 * @param color The color of the text to be drawn
 * @param x     The x position of the text to be drawn
 * @param y     The y position of the text to be drawn
 */
public record Text(String key, String value, Color color, int x, int y) {


}
