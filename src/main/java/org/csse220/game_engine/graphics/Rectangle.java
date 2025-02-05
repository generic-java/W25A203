package org.csse220.game_engine.graphics;

import org.csse220.game_engine.GameElement;
import org.csse220.game_engine.math_utils.Vector3d;

import java.awt.*;

public class Rectangle extends GameElement implements Drawable {

    private final Face top;
    private final Face bottom;
    private final Color color;

    public Rectangle(Point3d topLeft, Point3d topRight, Point3d bottomRight, Point3d bottomLeft, Color color) {
        top = new Face(topLeft, topRight, bottomLeft, color);
        bottom = new Face(bottomLeft, bottomRight, topRight, color);
        this.color = color;
    }

    public void draw(Vector3d camPose, double pitch, double yaw, boolean shade) {
        top.draw(camPose, pitch, yaw, shade);
        bottom.draw(camPose, pitch, yaw, shade);
    }

    public Color getColor() {
        return color;
    }
}
