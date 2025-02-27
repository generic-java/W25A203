package org.csse220.game_engine.graphics;

import org.csse220.game_engine.math.GamePose;
import org.csse220.game_engine.math.Vector3d;

import java.awt.*;

/**
 * Two faces slapped together to make a rectangle.  I hope you know what rectangle is.
 */
public class Rectangle extends Drawable {

    private final Face top;
    private final Face bottom;

    public Rectangle(GamePose pose, Point3d topLeft, Point3d topRight, Point3d bottomRight, Point3d bottomLeft, Color color) {
        super(pose, color);
        top = new Face(pose, topLeft, topRight, bottomLeft, color);
        bottom = new Face(pose, bottomLeft, bottomRight, topRight, color);
        setPose(pose);
    }

    public Rectangle(Point3d topLeft, Point3d topRight, Point3d bottomRight, Point3d bottomLeft, Color color) {
        this(new GamePose(), topLeft, topRight, bottomRight, bottomLeft, color);
    }

    @Override
    public void hide() {
        top.hide();
        bottom.hide();
    }

    @Override
    public void show() {
        top.show();
        bottom.show();
    }

    public void draw(Vector3d camPose, double pitch, double yaw, boolean shade) {
        top.draw(camPose, pitch, yaw, shade);
        bottom.draw(camPose, pitch, yaw, shade);
    }

    @Override
    public void setPose(GamePose pose) {
        super.setPose(pose);
        top.setPose(pose);
        bottom.setPose(pose);
    }

    public Face[] getFaces() {
        return new Face[]{top, bottom};
    }
}
