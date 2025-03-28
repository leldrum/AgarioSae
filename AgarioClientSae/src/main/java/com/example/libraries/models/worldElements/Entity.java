package com.example.libraries.models.worldElements;

import com.example.libraries.models.player.MoveableBodyModel;

import java.io.Serializable;
import java.util.Random;

public class Entity implements Serializable {
    private static final long serialVersionUID = 1L;

    private double x;
    private double y;
    protected double weight;
    private int r, g, b;

    public Entity(double x, double y, double weight) {
        this.x = x;
        this.y = y;
        this.weight = weight;

        Random rand = new Random();
        this.r = rand.nextInt(256);
        this.g = rand.nextInt(256);
        this.b = rand.nextInt(256);
    }

    public double getX() { return x; }
    public double getY() { return y; }
    public void setPosition(double x, double y) { this.x = x; this.y = y; }

    public double[] getPosition() { return new double[]{x, y}; }

    public double getWeight() { return weight; }
    public void setWeight(double weight) { this.weight = weight; }


    public int getR() { return r; }
    public int getG() { return g; }
    public int getB() { return b; }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Entity entity = (Entity) o;

        // Compare des propriétés significatives, comme les coordonnées X et Y, ou l'identifiant unique
        return Double.compare(entity.getX(), getX()) == 0 &&
                Double.compare(entity.getY(), getY()) == 0 &&
                Double.compare(entity.getWeight(), getWeight()) == 0;
    }


    public void update() {
    }

    public void onDeletion() {

    }
}
