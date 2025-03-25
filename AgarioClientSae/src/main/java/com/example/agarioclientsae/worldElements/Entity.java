package com.example.agarioclientsae.worldElements;

import java.util.Random;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

import java.util.Random;

public abstract class Entity extends Group{


    public Circle entity;
    private DoubleProperty weight = new SimpleDoubleProperty();


    private int r;
    private int g;
    private int b;
    private Label label;

    public Entity(Pane gamePane, double weight) {
        super();
        this.weight.set(weight);

        Random rand = new Random();
        r = rand.nextInt(255);
        g = rand.nextInt(255);
        b = rand.nextInt(255);

        entity = new Circle();
        entity.setFill(Color.rgb(r, g, b, 0.99));

        entity.radiusProperty().bind(Bindings.createDoubleBinding(
                () -> 10 * Math.sqrt(this.weight.get()),
                this.weight
        ));


        getChildren().add(entity);

        label = new Label("Entity");
        label.setTextFill(Color.WHITE);
        label.layoutXProperty().bind(entity.centerXProperty().subtract(label.widthProperty().divide(2)));
        label.layoutYProperty().bind(entity.centerYProperty().subtract(label.heightProperty().divide(2)));
        getChildren().add(label);

        World.getInstance().addEntity(this);
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
        return this.weight.get();
    }
    public void addWeight(double weight) {
        this.weight.set(this.weight.get() + weight);
    }

    public DoubleProperty weightProperty() {
        return this.weight;
    }


    public void Update(){

    }

    public void onDeletion(){

    }

}
