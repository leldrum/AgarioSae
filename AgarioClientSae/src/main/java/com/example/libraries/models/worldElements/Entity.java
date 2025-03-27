package com.example.libraries.models.worldElements;

import com.example.libraries.models.player.MoveableBodyModel;

import java.io.Serializable;
import java.util.Random;

public class Entity implements Serializable {
    private static final long serialVersionUID = 1L;

    private double x;
    private double y;
    private double weight;
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

    public void update() {
        // Logique de mise à jour (déplacement, actions, etc.)
    }

    public void onDeletion() {
        // Logique de suppression
    }
}
