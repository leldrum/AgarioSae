package com.example.client.views;

import com.example.libraries.models.worldElements.Food;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class FoodView extends Group {
    private Food food;
    private Circle circle;

    public FoodView(Food food) {
        this.food = food;

        circle = new Circle(10 * Math.sqrt(food.getWeight()), Color.GREEN);
        circle.setCenterX(food.getX());
        circle.setCenterY(food.getY());

        getChildren().add(circle);
    }

    public void updateView() {
        circle.setCenterX(food.getX());
        circle.setCenterY(food.getY());
    }
}
