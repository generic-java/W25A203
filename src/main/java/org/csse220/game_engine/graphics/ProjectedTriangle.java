package org.csse220.game_engine.graphics;

import org.csse220.game_engine.math_utils.Vector2d;

import java.awt.*;

public class ProjectedTriangle {
    private static final double MAX_DISTANCE = 500;
    private static final double MAX_DISPLAY_CUTOFF_ANGLE = 0.0000000000015;
    private static final double MIN_DISPLAY_CUTOFF_ANGLE = 0.00000000001;
    private static final double MAX_DISPLAY_CUTOFF_RATIO = 1.5;
    private static final double MIN_DISPLAY_CUTOFF_RATIO = 0.2;
    private static final double RATIO_STRETCH = 0.5;
    private static final double ANGLE_STRETCH = 1;

    private final ProjectedPoint[] vertices;
    private final DepthCalculator depthCalculator;
    private final boolean shouldDisplay;

    public ProjectedTriangle(double angle, ProjectedPoint point1, ProjectedPoint point2, ProjectedPoint point3, DepthCalculator depthCalculator) {
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
        Vector2d vector1 = new Vector2d(vertices[1].x() - vertices[0].x(), vertices[1].y() - vertices[0].y());
        Vector2d vector2 = new Vector2d(vertices[2].x() - vertices[0].x(), vertices[2].y() - vertices[0].y());
        Vector2d vector3 = new Vector2d(vertices[2].x() - vertices[1].x(), vertices[2].y() - vertices[1].y());
        double area = vector1.magnitude * vector2.magnitude * Math.sin(vector1.angleBetween(vector2)) / 2;
        double perimeter = vector1.magnitude + vector2.magnitude + vector3.magnitude;
        shouldDisplay = area / perimeter > minRatio(area) && angle >= 0 * minAngle(area);
        this.depthCalculator = depthCalculator;
    }

    private static double minRatio(double area) {
        return (MAX_DISPLAY_CUTOFF_RATIO - MIN_DISPLAY_CUTOFF_RATIO) * Math.exp(-RATIO_STRETCH * area) + MIN_DISPLAY_CUTOFF_RATIO;
    }

    private static double minAngle(double area) {
        double minAngle = (MAX_DISPLAY_CUTOFF_ANGLE - MIN_DISPLAY_CUTOFF_ANGLE) * Math.exp(-ANGLE_STRETCH * area) + MIN_DISPLAY_CUTOFF_ANGLE;
        return Double.isFinite(minAngle) ? minAngle : Double.POSITIVE_INFINITY;
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
            paintIfVisible(x, y, depthCalculator.calculateDepth(ProjectedPoint.xToActual(x), ProjectedPoint.yToActual(y)), color, Math.abs(topY - bottomY) <= 1);
        }
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
        if (!shouldDisplay) {
            return;
        }
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

    private void paintIfVisible(int x, int y, double depth, Color color, boolean thin) {
        if (depth < ZBuffer.getInstance().get(x, y) && depth > 0 && Double.isFinite(depth) && depth < MAX_DISTANCE) {
            Screen.getInstance().paintPixel(x, y, color);
            ZBuffer.getInstance().set(x, y, depth);
        }
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
}
