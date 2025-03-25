package com.example.agarioclientsae;

import java.util.Random;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

import java.util.Random;

abstract class Entity extends Group{


    public Circle entity;
    private double weight;
    private double size;

    public Entity(Group group, double weight) {
        super();
        this.weight = weight;


        Random rand = new Random();
        int r = rand.nextInt(255);
        int g = rand.nextInt(255);
        int b = rand.nextInt(255);

        size = 10 * Math.sqrt(this.weight);

        entity = new Circle(size, Color.rgb(r, g, b, 0.99));
        getChildren().add(entity);
        group.getChildren().add(this);
    }
    public double[] getPosition() {
        //returns current position of the sprite
        return new double[]{entity.getCenterX(), entity.getCenterY()};
    }

    public void Update(World world){

    }

    public void onDeletion(){

    }

}
