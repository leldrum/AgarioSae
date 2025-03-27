package com.example.client.controllers;

import com.example.client.views.MoveableBodyView;
import com.example.libraries.models.player.MoveableBodyModel;

public class MoveableBodyController {
    private MoveableBodyModel model;
    private MoveableBodyView view;

    public MoveableBodyController(MoveableBodyModel model, MoveableBodyView view) {
        this.model = model;
        this.view = view;
    }

    public void update(double[] velocity) {
        model.moveToward(velocity);
        view.updateView();
    }
}
