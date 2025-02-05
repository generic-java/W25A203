package org.csse220.game_engine.graphics;

import org.csse220.game_engine.GameElement;
import org.csse220.game_engine.kinematics.Hitbox;
import org.csse220.game_engine.math_utils.Vector3d;

import java.awt.*;

public class Cuboid extends GameElement implements Drawable {
    private final Point3d center;
    private final double width;
    private final double height;
    private final double depth;
    Rectangle[] rectangles = new Rectangle[6];

    public Cuboid(Point3d center, double width, double height, double depth, Color color) {
        this.center = center;
        this.width = width;
        this.height = height;
        this.depth = depth;
        double halfWidth = width / 2;
        double halfHeight = height / 2;
        double halfDepth = depth / 2;
        rectangles[0] = new Rectangle(
                center.translate(-halfWidth, halfDepth, halfHeight),
                center.translate(halfWidth, halfDepth, halfHeight),
                center.translate(halfWidth, -halfDepth, halfHeight),
                center.translate(-halfWidth, -halfDepth, halfHeight),
                color
        );
        rectangles[1] = new Rectangle(
                center.translate(-halfWidth, halfDepth, -halfHeight),
                center.translate(halfWidth, halfDepth, -halfHeight),
                center.translate(halfWidth, -halfDepth, -halfHeight),
                center.translate(-halfWidth, -halfDepth, -halfHeight),
                color
        );
        rectangles[2] = new Rectangle(
                center.translate(-halfWidth, halfDepth, -halfHeight),
                center.translate(-halfWidth, halfDepth, halfHeight),
                center.translate(-halfWidth, -halfDepth, halfHeight),
                center.translate(-halfWidth, -halfDepth, -halfHeight),
                color
        );
        rectangles[3] = new Rectangle(
                center.translate(halfWidth, halfDepth, -halfHeight),
                center.translate(halfWidth, halfDepth, halfHeight),
                center.translate(halfWidth, -halfDepth, halfHeight),
                center.translate(halfWidth, -halfDepth, -halfHeight),
                color
        );
        rectangles[4] = new Rectangle(
                center.translate(-halfWidth, halfDepth, -halfHeight),
                center.translate(halfWidth, halfDepth, -halfHeight),
                center.translate(halfWidth, halfDepth, halfHeight),
                center.translate(-halfWidth, halfDepth, halfHeight),
                color
        );
        rectangles[5] = new Rectangle(
                center.translate(-halfWidth, -halfDepth, -halfHeight),
                center.translate(halfWidth, -halfDepth, -halfHeight),
                center.translate(halfWidth, -halfDepth, halfHeight),
                center.translate(-halfWidth, -halfDepth, halfHeight),
                color
        );
    }

    public void draw(Vector3d camPose, double pitch, double yaw, boolean shade) {
        for (Rectangle rectangle : rectangles) {
            rectangle.draw(camPose, pitch, yaw, shade);
        }
    }

    public Hitbox toHitbox() {
        Vector3d absolutePose = center.getAbsolutePos();
        return new Hitbox(absolutePose.x(), absolutePose.y(), absolutePose.z(), width, height, depth);
    }
}
