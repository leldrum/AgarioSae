package com.example.agarioclientsae;

import java.util.Random;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

import java.util.Random;

abstract class Entity extends Pane {


    public Circle entity;
    private double weight;
    private double size;

    private int r;
    private int g;
    private int b;

    public Entity(Group group, double weight) {
        super();
        this.weight = weight;

        Random rand = new Random();
        r = rand.nextInt(255);
        g = rand.nextInt(255);
        b = rand.nextInt(255);

        size = 10 * Math.sqrt(this.weight);

        entity = new Circle(size, Color.rgb(r, g, b, 0.99));
        getChildren().add(entity);
        group.getChildren().add(this);
    }
    public double[] getPosition() {
        //returns current position of the sprite
        return new double[]{entity.getCenterX(), entity.getCenterY()};
    }

    public int getR() {
        return r;
    }

    public int getG() {
        return g;
    }

    public int getB() {
        return b;
    }

    public double getWeight() {
        return weight;
    }

    public double getSize() {
        return size;
    }

    public void Update(){

    }

    public void onDeletion(){

    }

}
