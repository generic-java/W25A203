package org.csse220.game_engine.graphics;

import org.csse220.game_engine.math_utils.GamePose;
import org.csse220.game_engine.math_utils.Vector2d;
import org.csse220.game_engine.math_utils.Vector3d;

import java.awt.*;


public class Face extends Drawable {

    public static final Vector3d LIGHT_SOURCE = new Vector3d(0.3, 0, 1);
    public static final double Y_CLIP_DISTANCE_BRIGHTNESS = 0.4;
    public static final double BRIGHTNESS_REDUCTION_FACTOR = (1 - Y_CLIP_DISTANCE_BRIGHTNESS) / Math.PI;
    private static final boolean CLIP_Y = true;
    private static final double CLIP_DISTANCE = 0.1;

    private final Point3d[] vertices;
    private final Color color;
    private final Color shadedColor;
    private final GamePose pose;

    public Face(GamePose pose, Point3d point1, Point3d point2, Point3d point3, Color color) {
        super(pose);
        vertices = new Point3d[]{point1, point2, point3};
        this.color = color;
        double angle = normalVector().angleBetween(LIGHT_SOURCE);
        if (angle > Math.PI / 2) {
            angle = Math.PI - angle;
        }
        double multiplier = 1 - BRIGHTNESS_REDUCTION_FACTOR * angle;
        shadedColor = new Color((int) (color.getRed() * multiplier), (int) (color.getGreen() * multiplier), (int) (color.getBlue() * multiplier));
        this.pose = pose;
    }

    public Face(Point3d point1, Point3d point2, Point3d point3, Color color) {
        this(new GamePose(), point1, point2, point3, color);
    }

    private DepthCalculator generateDepthCalculator(Vector3d camPos) {
        for (Point3d vertex : vertices) {
            vertex.calculateRelativePosition(camPos);
        }

        double c1 = -vertices[0].relativeX() * vertices[1].relativeY() * vertices[2].relativeZ() + vertices[0].relativeX() * vertices[2].relativeY() * vertices[1].relativeZ() + vertices[1].relativeX() * vertices[0].relativeY() * vertices[2].relativeZ() - vertices[1].relativeX() * vertices[2].relativeY() * vertices[0].relativeZ() - vertices[2].relativeX() * vertices[0].relativeY() * vertices[1].relativeZ() + vertices[2].relativeX() * vertices[1].relativeY() * vertices[0].relativeZ();
        double c2 = vertices[0].relativeX() * vertices[1].relativeY() - vertices[0].relativeX() * vertices[2].relativeY() - vertices[1].relativeX() * vertices[0].relativeY() + vertices[1].relativeX() * vertices[2].relativeY() + vertices[2].relativeX() * vertices[0].relativeY() - vertices[2].relativeX() * vertices[1].relativeY();
        double c3 = vertices[0].relativeY() * vertices[1].relativeZ() - vertices[0].relativeY() * vertices[2].relativeZ() - vertices[1].relativeY() * vertices[0].relativeZ() + vertices[1].relativeY() * vertices[2].relativeZ() + vertices[2].relativeY() * vertices[0].relativeZ() - vertices[2].relativeY() * vertices[1].relativeZ();
        double c4 = vertices[0].relativeX() * vertices[1].relativeZ() - vertices[0].relativeX() * vertices[2].relativeZ() - vertices[1].relativeX() * vertices[0].relativeZ() + vertices[1].relativeX() * vertices[2].relativeZ() + vertices[2].relativeX() * vertices[0].relativeZ() - vertices[2].relativeX() * vertices[1].relativeZ();

        return new SimpleDepthCalculator(c1, c2, c3, c4);
    }

    public void draw(Vector3d camPos, double pitch, double yaw, boolean shade) {
        DepthCalculator depthCalculator = generateDepthCalculator(camPos);
        int behindCamera = 0;
        int frontCamera = 0;
        ProjectedTriangle firstTriangle = null;
        ProjectedTriangle secondTriangle = null;
        Point3d[] behind = new Point3d[2];
        Point3d[] front = new Point3d[2];
        for (Point3d vertex : vertices) {
            vertex.calculateRelativePosition(camPos);
            if (vertex.relativeY() <= CLIP_DISTANCE) {
                if (behindCamera == 0) {
                    behind[0] = vertex;
                } else {
                    behind[1] = vertex;
                }
                behindCamera++;
            } else {
                if (frontCamera == 0) {
                    front[0] = vertex;
                } else {
                    front[1] = vertex;
                }
                frontCamera++;
            }
        }
        double newZ1;
        double newX1;
        double newZ2;
        double newX2;
        if (!CLIP_Y && behindCamera > 0) {
            return;
        }
        switch (behindCamera) {
            case 0:
                firstTriangle = new ProjectedTriangle(
                        vertices[0].project(),
                        vertices[1].project(),
                        vertices[2].project(),
                        depthCalculator
                );
                break;
            case 1:
                newZ1 = Vector2d.interpolate(behind[0].relativeY(), behind[0].relativeZ(), front[0].relativeY(), front[0].relativeZ(), CLIP_DISTANCE);
                newX1 = Vector2d.interpolate(behind[0].relativeY(), behind[0].relativeX(), front[0].relativeY(), front[0].relativeX(), CLIP_DISTANCE);
                newZ2 = Vector2d.interpolate(behind[0].relativeY(), behind[0].relativeZ(), front[1].relativeY(), front[1].relativeZ(), CLIP_DISTANCE);
                newX2 = Vector2d.interpolate(behind[0].relativeY(), behind[0].relativeX(), front[1].relativeY(), front[1].relativeX(), CLIP_DISTANCE);
                Point3d firstInterpolated = new Point3d(newX1, CLIP_DISTANCE, newZ1);
                Point3d secondInterpolated = new Point3d(newX2, CLIP_DISTANCE, newZ2);

                ProjectedPoint firstFront = front[0].project();
                ProjectedPoint secondFront = front[1].project();

                ProjectedPoint firstBehind = firstInterpolated.project();
                ProjectedPoint secondBehind = secondInterpolated.project();

                firstTriangle = new ProjectedTriangle(firstBehind, secondBehind, firstFront, depthCalculator);

                if (firstFront.x() > secondFront.x()) { // If firstFront is the rightmost front point, choose the leftmost behind point
                    secondTriangle = new ProjectedTriangle(firstFront, secondFront, firstBehind.x() < secondBehind.x() ? firstBehind : secondBehind, depthCalculator);
                } else { // If firstFront is leftmost, choose rightmost
                    secondTriangle = new ProjectedTriangle(firstFront, secondFront, firstBehind.x() > secondBehind.x() ? firstBehind : secondBehind, depthCalculator);
                }
                break;
            case 2:
                newZ1 = Vector2d.interpolate(front[0].relativeY(), front[0].relativeZ(), behind[0].relativeY(), behind[0].relativeZ(), CLIP_DISTANCE);
                newX1 = Vector2d.interpolate(front[0].relativeY(), front[0].relativeX(), behind[0].relativeY(), behind[0].relativeX(), CLIP_DISTANCE);
                newZ2 = Vector2d.interpolate(front[0].relativeY(), front[0].relativeZ(), behind[1].relativeY(), behind[1].relativeZ(), CLIP_DISTANCE);
                newX2 = Vector2d.interpolate(front[0].relativeY(), front[0].relativeX(), behind[1].relativeY(), behind[1].relativeX(), CLIP_DISTANCE);
                firstTriangle = new ProjectedTriangle(front[0].project(), Point3d.project(newX1, CLIP_DISTANCE, newZ1), Point3d.project(newX2, CLIP_DISTANCE, newZ2), depthCalculator);
                break;
            case 3:
                return;
        }
        if (secondTriangle != null) {
            //secondTriangle.fill(Color.orange);
            display(secondTriangle, shade);
        }
        if (behindCamera == 2 && false) {
            firstTriangle.fill(Color.gray);
        } else {
            display(firstTriangle, shade);
        }
    }

    @Override
    public void setPose(GamePose pose) {

    }

    private void display(ProjectedTriangle projection, boolean shade) {
        if (shade) {
            projection.fill(shadedColor);
        } else {
            projection.fill(color);
        }
    }

    private Vector3d normalVector() {
        Vector3d vector1 = vertices[1].relativeTo(vertices[0]);
        Vector3d vector2 = vertices[2].relativeTo(vertices[0]);
        return vector1.cross(vector2);
    }
}
