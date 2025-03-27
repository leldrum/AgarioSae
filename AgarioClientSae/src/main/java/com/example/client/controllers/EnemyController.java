package com.example.client.controllers;

import com.example.client.views.EnemyView;
import com.example.libraries.models.worldElements.EnemyModel;
import com.example.libraries.models.worldElements.WorldModel;

public class EnemyController {
    private EnemyModel model;
    private EnemyView view;

    public EnemyController(EnemyModel model, EnemyView view) {
        this.model = model;
        this.view = view;
    }

    public void update() {
        model.update(WorldModel.getInstance().getEntities());
        view.updateView();
    }
}
