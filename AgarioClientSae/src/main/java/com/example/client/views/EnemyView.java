package com.example.client.views;

import com.example.libraries.models.worldElements.EnemyModel;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class EnemyView {
    private EnemyModel model;
    private Circle sprite;

    public EnemyView(EnemyModel model) {
        this.model = model;
        this.sprite = new Circle(model.getWeight(), Color.RED);
        updateView();
    }

    public void updateView() {
        sprite.setCenterX(model.getX());
        sprite.setCenterY(model.getY());
    }

    public Circle getSprite() {
        return sprite;
    }

    public EnemyModel getModel() {
        return model;
    }
}
