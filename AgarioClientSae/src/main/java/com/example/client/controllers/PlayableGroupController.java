package com.example.client.controllers;

import com.example.client.app.HelloApplication;
import com.example.client.views.PlayableGroupView;
import com.example.libraries.models.player.PlayableGroupModel;


public class PlayableGroupController {
    private PlayableGroupModel model;
    private PlayableGroupView view;

    public PlayableGroupController(PlayableGroupModel model, PlayableGroupView view) {
        this.model = model;
        this.view = view;
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
    }
*/
    public void gameOver() {
        HelloApplication.timer.stop();
    }
}
