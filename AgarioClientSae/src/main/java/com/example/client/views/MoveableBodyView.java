package com.example.client.views;

import com.example.libraries.models.player.MoveableBodyModel;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class MoveableBodyView {
    private MoveableBodyModel model;
    private Circle sprite;

    public MoveableBodyView(MoveableBodyModel model) {
        this.model = model;
        this.sprite = new Circle(model.getWeight(), Color.BLUE);
        updateView();
    }

    public void updateView() {
        sprite.setCenterX(model.getX());
        sprite.setCenterY(model.getY());
    }

    public Circle getSprite() {
        return sprite;
    }
}
