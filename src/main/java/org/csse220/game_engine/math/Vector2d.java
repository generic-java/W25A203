package org.csse220.game_engine.math;

import java.util.Objects;


/**
 * An immutable class describing a vector in 2d space.
 */
public class Vector2d {
    public final double x;
    public final double y;
    public final double magnitude;
    public final double angle;

    public static final Vector2d undefined = new Vector2d(Double.NaN, Double.NaN);

    /**
     * If isCartesian is set to false, var1 and var2 will be interpreted respectively as r and theta.
     **/
    public Vector2d(double var1, double var2, boolean isCartesian) {
        if (isCartesian) {
            x = var1;
            y = var2;
            magnitude = calculateMagnitude(var1, var2);
            angle = calculateAngle(var1, var2);
        } else {
            x = var1 * Math.cos(var2);
            y = var1 * Math.sin(var2);
            magnitude = var1;
            angle = var2 % (2 * Math.PI);
        }
    }

    public Vector2d(double x, double y) {
        this(x, y, true);
    }

    public Vector2d() {
        this(0, 0);
    }

    public static double interpolate(double x1, double y1, double x2, double y2, double x) {
        if (x2 == x1) {
            throw new ArithmeticException("Error: division by zero in interpolation function");
        }
        return y1 + (x - x1) / (x2 - x1) * (y2 - y1);
    }

    private double calculateMagnitude(double x, double y) {
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }

    private double calculateAngle(double x, double y) {
        if (x == 0.0) {
            if (y < 0) {
                return -Math.PI / 2;
            } else {
                return Math.PI / 2;
            }
        } else if (x >= 0.0) {
            return Math.atan(y / x);
        } else {
            return Math.atan(y / x) + Math.PI;
        }
    }

    public Vector2d rotate(double angle) {
        return new Vector2d(this.magnitude, this.angle + angle, false);
    }

    public double distanceTo(Vector2d vector) {
        return Math.sqrt(Math.pow((vector.x - x), 2) + Math.pow((vector.y - y), 2));
    }

    public Vector2d add(Vector2d translation) {
        return new Vector2d(translation.x + x, translation.y + y);
    }

    public Vector2d multiply(double scalar) {
        return new Vector2d(x * scalar, y * scalar);
    }

    public Vector2d translate(double x, double y) {
        return new Vector2d(this.x + x, this.y + y);
    }

    public Vector2d normalize() {
        return magnitude == 0 ? this : multiply(1 / magnitude);
    }

    public double dot(Vector2d other) {
        return x * other.x + y * other.y;
    }

    public double angleBetween(Vector2d other) {
        return Math.acos(dot(other) / (magnitude * other.magnitude));
    }

    @Override
    public boolean equals(Object vector) {
        if (vector == null) {
            return false;
        } else if (vector instanceof Vector2d toCompare) {
            if (!(Double.isFinite(toCompare.x) && Double.isFinite(toCompare.y)) && !(Double.isFinite(x) && Double.isFinite(y))) {
                return true;
            }
            return Objects.equals(x, toCompare.x) && Objects.equals(y, toCompare.y);
        }
        return false;
    }

    @Override
    public String toString() {
        return "x: " + x + ", y: " + y;
    }

}