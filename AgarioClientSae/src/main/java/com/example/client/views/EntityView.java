package com.example.client.views;

import com.example.libraries.models.worldElements.Entity;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class EntityView extends Group {
    private Entity entity;
    private Circle circle;
    private Label label;

    public EntityView(Entity entity) {
        this.entity = entity;

        circle = new Circle(10 * Math.sqrt(entity.getWeight()), Color.rgb(entity.getR(), entity.getG(), entity.getB(), 0.99));
        circle.setCenterX(entity.getX());
        circle.setCenterY(entity.getY());


        label = new Label("Entity");
        label.setTextFill(Color.WHITE);
        label.layoutXProperty().bind(circle.centerXProperty().subtract(label.widthProperty().divide(2)));
        label.layoutYProperty().bind(circle.centerYProperty().subtract(label.heightProperty().divide(2)));

        getChildren().addAll(circle,label);
    }

    public void updateView() {
        circle.setRadius(10 * Math.sqrt(entity.getWeight()));
        circle.setCenterX(entity.getX());
        circle.setCenterY(entity.getY());
    }
}
