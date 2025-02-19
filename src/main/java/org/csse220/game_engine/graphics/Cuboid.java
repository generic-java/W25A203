package org.csse220.game_engine.graphics;

import org.csse220.game_engine.kinematics.Hitbox;
import org.csse220.game_engine.math.GamePose;
import org.csse220.game_engine.math.Vector3d;

import java.awt.*;

public class Cuboid extends Drawable {
    private final GamePose center;
    private final double width;
    private final double height;
    private final double depth;
    Rectangle[] rectangles = new Rectangle[6];

    public Cuboid(GamePose pose, GamePose center, double width, double height, double depth, Color color) {
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
                center.translate(-halfWidth, halfDepth, halfHeight).rotateYaw(this.center, center.yaw()).toPoint3d(),
                center.translate(halfWidth, halfDepth, halfHeight).rotateYaw(this.center, center.yaw()).toPoint3d(),
                center.translate(halfWidth, -halfDepth, halfHeight).rotateYaw(this.center, center.yaw()).toPoint3d(),
                center.translate(-halfWidth, -halfDepth, halfHeight).rotateYaw(this.center, center.yaw()).toPoint3d(),
                color
        );
        rectangles[1] = new Rectangle(
                pose,
                center.translate(-halfWidth, halfDepth, -halfHeight).rotateYaw(this.center, center.yaw()).toPoint3d(),
                center.translate(halfWidth, halfDepth, -halfHeight).rotateYaw(this.center, center.yaw()).toPoint3d(),
                center.translate(halfWidth, -halfDepth, -halfHeight).rotateYaw(this.center, center.yaw()).toPoint3d(),
                center.translate(-halfWidth, -halfDepth, -halfHeight).rotateYaw(this.center, center.yaw()).toPoint3d(),
                color
        );
        rectangles[2] = new Rectangle(
                pose,
                center.translate(-halfWidth, halfDepth, -halfHeight).rotateYaw(this.center, center.yaw()).toPoint3d(),
                center.translate(-halfWidth, halfDepth, halfHeight).rotateYaw(this.center, center.yaw()).toPoint3d(),
                center.translate(-halfWidth, -halfDepth, halfHeight).rotateYaw(this.center, center.yaw()).toPoint3d(),
                center.translate(-halfWidth, -halfDepth, -halfHeight).rotateYaw(this.center, center.yaw()).toPoint3d(),
                color
        );
        rectangles[3] = new Rectangle(
                pose,
                center.translate(halfWidth, halfDepth, -halfHeight).rotateYaw(this.center, center.yaw()).toPoint3d(),
                center.translate(halfWidth, halfDepth, halfHeight).rotateYaw(this.center, center.yaw()).toPoint3d(),
                center.translate(halfWidth, -halfDepth, halfHeight).rotateYaw(this.center, center.yaw()).toPoint3d(),
                center.translate(halfWidth, -halfDepth, -halfHeight).rotateYaw(this.center, center.yaw()).toPoint3d(),
                color
        );
        rectangles[4] = new Rectangle(
                pose,
                center.translate(-halfWidth, halfDepth, -halfHeight).rotateYaw(this.center, center.yaw()).toPoint3d(),
                center.translate(halfWidth, halfDepth, -halfHeight).rotateYaw(this.center, center.yaw()).toPoint3d(),
                center.translate(halfWidth, halfDepth, halfHeight).rotateYaw(this.center, center.yaw()).toPoint3d(),
                center.translate(-halfWidth, halfDepth, halfHeight).rotateYaw(this.center, center.yaw()).toPoint3d(),
                color
        );
        rectangles[5] = new Rectangle(
                pose,
                center.translate(-halfWidth, -halfDepth, -halfHeight).rotateYaw(this.center, center.yaw()).toPoint3d(),
                center.translate(halfWidth, -halfDepth, -halfHeight).rotateYaw(this.center, center.yaw()).toPoint3d(),
                center.translate(halfWidth, -halfDepth, halfHeight).rotateYaw(this.center, center.yaw()).toPoint3d(),
                center.translate(-halfWidth, -halfDepth, halfHeight).rotateYaw(this.center, center.yaw()).toPoint3d(),
                color
        );
    }

    public Cuboid(GamePose center, double width, double height, double depth, Color color) {
        this(center, center, width, height, depth, color);
    }

    @Override
    public void hide() {
        for (Rectangle rect : rectangles) {
            rect.hide();
        }
    }

    @Override
    public void show() {
        for (Rectangle rect : rectangles) {
            rect.show();
        }
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
        return center.toPoint3d();
    }

    public Hitbox toHitbox() {
        return new Hitbox(center, width, height, depth);
    }
}
