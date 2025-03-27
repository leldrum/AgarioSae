package com.example.client.controllers;

import com.example.client.views.EntityView;
import com.example.client.views.MoveableBodyView;
import com.example.libraries.models.worldElements.WorldModel;
import com.example.libraries.views.WorldView;

public class WorldController {
    private WorldModel world;
    private WorldView view;

    private PlayableGroupController pc;

    private EntityView entityView;

    private MoveableBodyView mbv;

    public WorldController(WorldModel world, WorldView view, PlayableGroupController pc, EntityView entityView, MoveableBodyView mbv) {
        this.world = world;
        this.view = view;
        this.pc = pc;
        this.entityView = entityView;
        this.mbv = mbv;
    }

    public void update() {
        world.updateWorld();
        view.updateMinimap(world);
        view.updateLeaderboard(world);
        pc.update();
        entityView.updateView();
        mbv.updateView();


        System.out.println(world.getEntities().get(0).getX() + " " + world.getEntities().get(0).getY());
    }

    public WorldModel getWorld() {
        return world;
    }
}
