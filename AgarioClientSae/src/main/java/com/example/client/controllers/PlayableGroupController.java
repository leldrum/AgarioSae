package com.example.client.controllers;

import com.example.client.app.HelloApplication;
import com.example.client.views.EntityView;
import com.example.client.views.PlayableGroupView;
import com.example.libraries.models.player.PlayableGroupModel;
import com.example.libraries.models.player.PlayerModel;


public class PlayableGroupController {
    private PlayerModel model;
    private PlayableGroupView view;

    private MoveableBodyController moveable;

    private EntityView entityView;

    public PlayableGroupController(PlayerModel model, PlayableGroupView view,MoveableBodyController moveable, EntityView entityView) {
        this.model = model;
        this.view = view;
        this.moveable = moveable;
        this.entityView = entityView;
    }

    public void update() {
        double[] mousePos = HelloApplication.getMousePosition();
        model.checkCollision();

        if (model.isGameOver()) {
            gameOver();
        } else {
            double x = model.getPosition()[0];
            double y = model.getPosition()[1];
            view.updateCameraPosition(x, y);
            moveable.update(mousePos);
            entityView.updateView();
        }
    }

    public void increaseSize(double foodValue) {
        model.increaseSize(foodValue);
        view.zoom(foodValue / 150);
    }

    /*
    public void divide() {
        model.divide();
    }

    public void union() {
        model.union();
    }*/

    public void gameOver() {
        HelloApplication.timer.stop();
    }
}
