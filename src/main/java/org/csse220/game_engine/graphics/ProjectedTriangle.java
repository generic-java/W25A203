package org.csse220.game_engine.graphics;

import org.csse220.game_engine.kinematics.LineSegment;

import java.awt.*;

public class ProjectedTriangle {
    private static final double MAX_DISTANCE = 500;

    private final double angle;
    private final ProjectedPoint[] vertices;
    private double c1;
    private double c2;
    private double c3;
    private double c4;
    private final DepthCalculator depthCalculator;
    private static final double DEPTH_ALLOWANCE = -0.5;

    public ProjectedTriangle(double angle, ProjectedPoint point1, ProjectedPoint point2, ProjectedPoint point3, DepthCalculator depthCalculator) {
        this.angle = angle;
        vertices = new ProjectedPoint[]{point1, point2, point3}; // sorts in increasing order by x value
        for (int i = 0; i < vertices.length; i++) {
            if (vertices[0].x() > vertices[1].x()) {
                swap(vertices, 0, 1);
            }
            if (vertices[0].x() > vertices[2].x()) {
                swap(vertices, 1, 2);
            }
            if (vertices[1].x() > vertices[2].x()) {
                swap(vertices, 1, 2);
            }
        }
        this.depthCalculator = depthCalculator;
    }

    public ProjectedTriangle(ProjectedPoint point1, ProjectedPoint point2, ProjectedPoint point3, DepthCalculator depthCalculator) {
        this(0, point1, point2, point3, depthCalculator);
    }

    private static void swap(ProjectedPoint[] array, int index1, int index2) {
        ProjectedPoint temp = array[index2];
        array[index2] = array[index1];
        array[index1] = temp;
    }

    private void fillHalf(Color color, int topY, int bottomY, int x) {
        topY = Math.clamp(topY, -1, Screen.getInstance().getAdjustedHeight() + 1);
        bottomY = Math.clamp(bottomY, -1, Screen.getInstance().getAdjustedHeight() + 1);
        for (int j = 0; j <= Math.abs(topY - bottomY); j++) {
            int y = topY - j * polarity(topY - bottomY);
            paintIfVisible(x, y, depthCalculator.calculateDepth(ProjectedPoint.xToActual(x), ProjectedPoint.yToActual(y)), color);
        }
    }

    private static boolean withinScreenWidth(int x) {
        return x >= 0 && x < Screen.getInstance().getAdjustedWidth();
    }

    private boolean outsideScreen() {
        double screenWidth = Screen.getInstance().getAdjustedWidth();
        double screenHeight = Screen.getInstance().getAdjustedHeight();
        return (vertices[0].x() < 0 && vertices[1].x() < 0 && vertices[2].x() < 0)
                || (vertices[0].x() > screenWidth && vertices[1].x() > screenWidth && vertices[2].x() > screenWidth)
                || (vertices[0].y() < 0 && vertices[1].y() < 0 && vertices[2].y() < 0)
                || (vertices[0].y() > screenHeight && vertices[1].y() > screenHeight && vertices[2].y() > screenHeight);
    }

    public void fill(Color color) {
        if (!outsideScreen()) {
            if (vertices[0].x() != vertices[1].x() && vertices[0].x() != vertices[2].x()) {
                for (int i = Math.clamp(vertices[0].x(), -1, Screen.getInstance().getAdjustedWidth() + 1); i <= Math.clamp(vertices[1].x(), -1, Screen.getInstance().getAdjustedWidth() + 1); i++) {
                    int topY = interpolateY(vertices[0], vertices[2], i);
                    int bottomY = interpolateY(vertices[0], vertices[1], i);
                    if (i > 0 && i < Screen.getInstance().getAdjustedWidth()) {
                        fillHalf(color, topY, bottomY, i);
                    }
                }
            }
            if (vertices[0].x() != vertices[2].x() && vertices[1].x() != vertices[2].x()) {
                for (int i = Math.clamp(vertices[1].x(), -1, Screen.getInstance().getAdjustedWidth() + 1); i <= Math.clamp(vertices[2].x(), -1, Screen.getInstance().getAdjustedWidth() + 1); i++) {
                    int topY = interpolateY(vertices[0], vertices[2], i);
                    int bottomY = interpolateY(vertices[1], vertices[2], i);
                    if (i > 0 && i < Screen.getInstance().getAdjustedWidth()) {
                        fillHalf(color, topY, bottomY, i);
                    }
                }
            }
        }
    }

    private void paintIfVisible(int x, int y, double depth, Color color) {
        double[] zBufferInformation = ZBuffer.getInstance().get(x, y);
        double depthDifference = zBufferInformation[0] - depth;
        double angleDifference = zBufferInformation[1] - angle;
        if ((depthDifference > 0 || (depthDifference > DEPTH_ALLOWANCE && angleDifference > 0)) && depth > 0 && Double.isFinite(depth) && depth < MAX_DISTANCE) {
            Screen.getInstance().paintPixel(x, y, color);
            ZBuffer.getInstance().set(x, y, depth, angle);
        }
    }

    /**
     * @param x1 The x coordinate of the first vertex of the triangle
     * @param y1 The y coordinate of the first vertex of the triangle
     * @param z1 The depth of the point corresponding to the first vertex of the triangle relative to the player after being rotated in 3D space.
     *           That is, the transformed y coordinate of the pixel in 3D space.
     * @param x2 The x coordinate of the second vertex of the triangle
     * @param y2 The y coordinate of the second vertex of the triangle
     * @param z2 The depth of the point corresponding to the second vertex of the triangle relative to the player after being rotated in 3D space.
     *           That is, the transformed y coordinate of the pixel in 3D space.
     * @param x3 The x coordinate of the third vertex of the triangle
     * @param y3 The y coordinate of the third vertex of the triangle
     * @param z3 The depth of the point corresponding to the third vertex of the triangle relative to the player after being rotated in 3D space.
     *           That is, the transformed y coordinate of the pixel in 3D space.
     */

    private void generatePixelDepthConstants(double x1, double y1, double z1, double x2, double y2, double z2, double x3, double y3, double z3) {
        c1 = (y2 * z3 - z2 * y3) * x1 + (y3 * z1 - z3 * y1) * x2 + (z2 * y1 - y2 * z1) * x3;
        c2 = (z2 - z3) * x1 + (z3 - z1) * x2 + (z1 - z2) * x3;
        c3 = (z2 - z3) * y1 + (z3 - z1) * y2 + (z1 - z2) * y3;
        c4 = (y2 - y3) * x1 + (y3 - y1) * x2 + (y1 - y2) * x3;
    }

    private double getPixelDepth(double x, double y) {
        return (c1 + c2 * y - c3 * x) / c4;
    }

    private static int polarity(double number) {
        return (int) (number == 0 ? 1 : number / Math.abs(number));
    }

    private static int interpolateY(ProjectedPoint point1, ProjectedPoint point2, int x) {
        return (int) ((x - point1.x()) / (double) (point2.x() - point1.x()) * (point2.y() - point1.y()) + point1.y());
    }

    public ProjectedPoint[] getVertices() {
        return vertices;
    }

    private LineSegment[] getLineSegments() {
        return new LineSegment[]{
                new LineSegment(vertices[0].toPoint2d(), vertices[1].toPoint2d()),
                new LineSegment(vertices[1].toPoint2d(), vertices[2].toPoint2d()),
                new LineSegment(vertices[2].toPoint2d(), vertices[0].toPoint2d())
        };
    }


    public double intersectionsWith(ProjectedTriangle other) {
        int intersections = 0;
        for (LineSegment lineSegment : getLineSegments()) {
            for (LineSegment toCheck : other.getLineSegments()) {
                if (lineSegment.intersectsWith(toCheck)) {
                    intersections++;
                }
            }
        }
        return intersections;
    }
}
