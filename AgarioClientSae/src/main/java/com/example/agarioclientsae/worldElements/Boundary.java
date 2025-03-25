package com.example.agarioclientsae.worldElements;

public class Boundary {
    int xMin, yMin, xMax, yMax;

    public Boundary(int xMin, int yMin, int xMax, int yMax) {
        super();
        this.xMin = xMin;
        this.yMin = yMin;
        this.xMax = xMax;
        this.yMax = yMax;
    }


    public boolean inRange(int x, int y) {
        return (x >= xMin && x < xMax) && (y >= yMin && y < yMax);
    }


    public int getxMin() {
        return xMin;
    }

    public int getyMin() {
        return yMin;
    }

    public int getxMax() {
        return xMax;
    }

    public int getyMax() {
        return yMax;
    }
}

