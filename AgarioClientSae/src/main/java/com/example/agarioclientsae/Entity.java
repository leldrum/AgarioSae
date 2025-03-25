package com.example.agarioclientsae;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.Random;

public abstract class Entity {

    public Circle entity;
    private double weight;
    private double size;

    public Entity(double weight){
        this.weight = weight;

        Random rand = new Random();
        int r = rand.nextInt(255);
        int g = rand.nextInt(255);
        int b = rand.nextInt(255);

        size = 10 * Math.sqrt(this.weight);

        entity = new Circle(size, Color.rgb(r,g,b,0.99));

    }
}
