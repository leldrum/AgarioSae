package com.example.agarioclientsae;

public class Boundary {
    private double x, y; // Position de l'angle supérieur gauche
    private double width, height; // Largeur et hauteur de la zone

    public Boundary(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public boolean inRange(double px, double py) {
        return px >= x && px <= x + width && py >= y && py <= y + height;
    }


    // Getters and setters
    public double getX() { return x; }
    public double getY() { return y; }
    public double getWidth() { return width; }
    public double getHeight() { return height; }
}

