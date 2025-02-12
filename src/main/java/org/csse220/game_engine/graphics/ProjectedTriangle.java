package org.csse220.game_engine.graphics;

import java.awt.*;

public class ProjectedTriangle {
    private static final double MAX_DISTANCE = 500;

    private final ProjectedPoint[] vertices;
    private final DepthCalculator depthCalculator;
    private final double angle;

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
