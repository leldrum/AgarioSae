package com.example.client.views;

import com.example.libraries.models.worldElements.Entity;
import com.example.libraries.models.worldElements.WorldModel;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.HashMap;

public class EntitiesView extends Group {

    private ArrayList<Circle> circles = new ArrayList<>();

    private ArrayList<Label> labels = new ArrayList<>();

    private ArrayList<Entity> list = new ArrayList<>();


    public EntitiesView(ArrayList<Entity> list) {
        for (Entity entity : list) {
            list.add(entity);
        }
    }
    public void entityView() {
        int cp = 0;
        for (Entity entity : list) {

            circles.add(new Circle(10 * Math.sqrt(entity.getWeight()), Color.rgb(entity.getR(), entity.getG(), entity.getB(), 0.99)));
            circles.get(cp).setCenterX(entity.getX());
            circles.get(cp).setCenterY(entity.getY());


            labels.add(new Label("Entity"));
            labels.get(cp).setTextFill(Color.WHITE);
            labels.get(cp).layoutXProperty().bind(circles.get(cp).centerXProperty().subtract(labels.get(cp).widthProperty().divide(2)));
            labels.get(cp).layoutYProperty().bind(circles.get(cp).centerYProperty().subtract(labels.get(cp).heightProperty().divide(2)));

            getChildren().addAll(circles.get(cp), labels.get(cp));
            cp++;
        }
    }

    public void updateView() {
        for (Entity entity : list) {
            //circle.setRadius(10 * Math.sqrt(entity.getWeight()));
            circle.setCenterX(entity.getX());
            circle.setCenterY(entity.getY());
        }
    }
}
