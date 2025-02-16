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

    private final Vector3d[] relativeVertices;
    private final Point3d[] vertices;
    private Color shadedColor;
    private boolean hidden;

    public Face(GamePose pose, Point3d point1, Point3d point2, Point3d point3, Color color) {
        super(pose, color);
        vertices = new Point3d[]{point1, point2, point3};
        relativeVertices = new Vector3d[]{point1.relativeTo(pose), point2.relativeTo(pose), point3.relativeTo(pose)};
        shadedColor = calculateShadedColor();
    }

    private Color calculateShadedColor() {
        double angle = normalVector().angleBetween(LIGHT_SOURCE);
        if (angle > Math.PI / 2) {
            angle = Math.PI - angle;
        }
        double multiplier = 1 - BRIGHTNESS_REDUCTION_FACTOR * angle;
        return new Color((int) (color.getRed() * multiplier), (int) (color.getGreen() * multiplier), (int) (color.getBlue() * multiplier));
    }

    public Face(Point3d point1, Point3d point2, Point3d point3, Color color) {
        this(new GamePose(), point1, point2, point3, color);
    }

    private DepthCalculator generateDepthCalculator(Vector3d camPos) {
        for (Point3d vertex : vertices) {
            vertex.calculateRelativePosition();
        }

        double c1 = -vertices[0].relativeX() * vertices[1].relativeY() * vertices[2].relativeZ() + vertices[0].relativeX() * vertices[2].relativeY() * vertices[1].relativeZ() + vertices[1].relativeX() * vertices[0].relativeY() * vertices[2].relativeZ() - vertices[1].relativeX() * vertices[2].relativeY() * vertices[0].relativeZ() - vertices[2].relativeX() * vertices[0].relativeY() * vertices[1].relativeZ() + vertices[2].relativeX() * vertices[1].relativeY() * vertices[0].relativeZ();
        double c2 = vertices[0].relativeX() * vertices[1].relativeY() - vertices[0].relativeX() * vertices[2].relativeY() - vertices[1].relativeX() * vertices[0].relativeY() + vertices[1].relativeX() * vertices[2].relativeY() + vertices[2].relativeX() * vertices[0].relativeY() - vertices[2].relativeX() * vertices[1].relativeY();
        double c3 = vertices[0].relativeY() * vertices[1].relativeZ() - vertices[0].relativeY() * vertices[2].relativeZ() - vertices[1].relativeY() * vertices[0].relativeZ() + vertices[1].relativeY() * vertices[2].relativeZ() + vertices[2].relativeY() * vertices[0].relativeZ() - vertices[2].relativeY() * vertices[1].relativeZ();
        double c4 = vertices[0].relativeX() * vertices[1].relativeZ() - vertices[0].relativeX() * vertices[2].relativeZ() - vertices[1].relativeX() * vertices[0].relativeZ() + vertices[1].relativeX() * vertices[2].relativeZ() + vertices[2].relativeX() * vertices[0].relativeZ() - vertices[2].relativeX() * vertices[1].relativeZ();

        return new SimpleDepthCalculator(c1, c2, c3, c4);
    }

    public void draw(Vector3d camPos, double pitch, double yaw, boolean shade) {
        if (hidden) {
            return;
        }
        DepthCalculator depthCalculator = generateDepthCalculator(camPos);
        int behindCamera = 0;
        int frontCamera = 0;
        ProjectedTriangle firstTriangle = null;
        Point3d[] behind = new Point3d[2];
        Point3d[] front = new Point3d[2];
        for (Point3d vertex : vertices) {
            vertex.calculateRelativePosition();
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
                        visibleAngle(),
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

                ProjectedPoint firstFront = front[0].project();
                ProjectedPoint secondFront = front[1].project();

                ProjectedPoint firstBehind = Point3d.project(newX1, CLIP_DISTANCE, newZ1);
                ProjectedPoint secondBehind = Point3d.project(newX2, CLIP_DISTANCE, newZ2);

                firstTriangle = new ProjectedTriangle(firstBehind, secondBehind, firstFront, depthCalculator);

                ProjectedTriangle firstCandidate = new ProjectedTriangle(firstFront, secondFront, firstBehind, depthCalculator);
                ProjectedTriangle secondCandidate = new ProjectedTriangle(firstFront, secondFront, secondBehind, depthCalculator);

                display(firstCandidate, shade);
                display(secondCandidate, shade);
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
        display(firstTriangle, shade);
    }

    @Override
    public void setPose(GamePose pose) {
        super.setPose(pose);
        for (int i = 0; i < vertices.length; i++) {
            vertices[i] = new Point3d(relativeVertices[i].rotateYaw(pose.yaw()).translate(pose));
        }
        shadedColor = calculateShadedColor();
    }

    private void display(ProjectedTriangle projection, boolean shade) {
        if (shade) {
            projection.fill(shadedColor);
        } else {
            projection.fill(color);
        }
    }

    private Vector3d normalVector() {
        Vector3d vector1 = relativeVertices[1].relativeTo(relativeVertices[0]);
        Vector3d vector2 = relativeVertices[2].relativeTo(relativeVertices[0]);
        return vector1.cross(vector2);
    }

    private Vector3d currentNormalVector() {
        for (Point3d vertex : vertices) {
            vertex.calculateRelativePosition();
        }
        Vector3d vector2 = vertices[2].relativePose().relativeTo(vertices[0].relativePose());
        Vector3d vector1 = vertices[1].relativePose().relativeTo(vertices[0].relativePose());
        return vector1.cross(vector2);
    }

    private static final double MIN_ANGLE = Math.toRadians(0.5);

    private boolean clearlyVisible() {
        double firstAngle = currentNormalVector().angleBetween(new Vector3d(1, 0, 0));
        firstAngle = firstAngle > Math.PI / 2 ? Math.PI - firstAngle : firstAngle;

        double secondAngle = currentNormalVector().angleBetween(new Vector3d(0, 0, 1));
        secondAngle = secondAngle > Math.PI / 2 ? Math.PI - secondAngle : secondAngle;

        //System.out.println(currentNormalVector());

        return Math.abs(firstAngle) > MIN_ANGLE && Math.abs(secondAngle) > MIN_ANGLE;
    }

    private double visibleAngle() {
        double secondAngle = currentNormalVector().angleBetween(new Vector3d(0, 0, 1));
        secondAngle = secondAngle > Math.PI / 2 ? Math.PI - secondAngle : secondAngle;
        return Math.abs(secondAngle);
    }

    @Override
    public void show() {
        hidden = false;
    }

    @Override
    public void hide() {
        hidden = true;
    }

    public boolean getHidden() {
        return hidden;
    }
}
