package org.csse220.game_engine.graphics;

import org.csse220.game_engine.kinematics.Hitbox;
import org.csse220.game_engine.math_utils.GamePose;
import org.csse220.game_engine.math_utils.Vector3d;

import java.awt.*;

public class Cuboid extends Drawable {
    private final Point3d center;
    private final double width;
    private final double height;
    private final double depth;
    Rectangle[] rectangles = new Rectangle[6];

    public Cuboid(GamePose pose, Point3d center, double width, double height, double depth, Color color) {
        super(pose, color);
        this.center = center;
        this.width = width;
        this.height = height;
        this.depth = depth;
        double halfWidth = width / 2;
        double halfHeight = height / 2;
        double halfDepth = depth / 2;
        rectangles[0] = new Rectangle(
                pose,
                center.translate(-halfWidth, halfDepth, halfHeight),
                center.translate(halfWidth, halfDepth, halfHeight),
                center.translate(halfWidth, -halfDepth, halfHeight),
                center.translate(-halfWidth, -halfDepth, halfHeight),
                color
        );
        rectangles[1] = new Rectangle(
                pose,
                center.translate(-halfWidth, halfDepth, -halfHeight),
                center.translate(halfWidth, halfDepth, -halfHeight),
                center.translate(halfWidth, -halfDepth, -halfHeight),
                center.translate(-halfWidth, -halfDepth, -halfHeight),
                color
        );
        rectangles[2] = new Rectangle(
                pose,
                center.translate(-halfWidth, halfDepth, -halfHeight),
                center.translate(-halfWidth, halfDepth, halfHeight),
                center.translate(-halfWidth, -halfDepth, halfHeight),
                center.translate(-halfWidth, -halfDepth, -halfHeight),
                color
        );
        rectangles[3] = new Rectangle(
                pose,
                center.translate(halfWidth, halfDepth, -halfHeight),
                center.translate(halfWidth, halfDepth, halfHeight),
                center.translate(halfWidth, -halfDepth, halfHeight),
                center.translate(halfWidth, -halfDepth, -halfHeight),
                color
        );
        rectangles[4] = new Rectangle(
                pose,
                center.translate(-halfWidth, halfDepth, -halfHeight),
                center.translate(halfWidth, halfDepth, -halfHeight),
                center.translate(halfWidth, halfDepth, halfHeight),
                center.translate(-halfWidth, halfDepth, halfHeight),
                color
        );
        rectangles[5] = new Rectangle(
                pose,
                center.translate(-halfWidth, -halfDepth, -halfHeight),
                center.translate(halfWidth, -halfDepth, -halfHeight),
                center.translate(halfWidth, -halfDepth, halfHeight),
                center.translate(-halfWidth, -halfDepth, halfHeight),
                color
        );
        setPose(pose);
    }

    public Cuboid(Point3d center, double width, double height, double depth, Color color) {
        this(new GamePose(), center, width, height, depth, color);
    }

    public void draw(Vector3d camPose, double pitch, double yaw, boolean shade) {
        for (Rectangle rectangle : rectangles) {
            rectangle.draw(camPose, pitch, yaw, shade);
        }
    }

    @Override
    public void setPose(GamePose pose) {
        super.setPose(pose);
        for (Rectangle rectangle : rectangles) {
            rectangle.setPose(pose);
        }
    }

    public Point3d getCenter() {
        return center;
    }

    public Hitbox toHitbox() {
        return new Hitbox(center.toGamePose(), width, height, depth);
    }
}
