package com.example.libraries.worldElements;

public class Boundary {
    public final double xMin, yMin, xMax, yMax;

    public Boundary(double xMin, double yMin, double xMax, double yMax) {
        this.xMin = xMin;
        this.yMin = yMin;
        this.xMax = xMax;
        this.yMax = yMax;
    }

    public boolean contains(double x, double y) {
        return (x >= xMin && x <= xMax) && (y >= yMin && y <= yMax);
    }

    public boolean intersects(Boundary other) {
        return !(other.xMax < xMin || other.xMin > xMax ||
                other.yMax < yMin || other.yMin > yMax);
    }
}