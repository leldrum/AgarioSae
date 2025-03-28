package com.example.client.controllers;

import com.example.client.views.EntitiesView;
import com.example.client.views.WorldView;
import com.example.libraries.models.worldElements.WorldModel;

import static com.example.client.app.HelloApplication.root;

public class WorldController {
    private WorldModel world;
    private WorldView view;

    private PlayableGroupController pc;

    private EntitiesView entityView;

    private MoveableBodyController mbc;

    public WorldController(WorldModel world, WorldView view, PlayableGroupController pc, EntitiesView entityView, MoveableBodyController mbc) {
        this.world = world;
        this.view = view;
        this.pc = pc;
        this.entityView = entityView;
        this.mbc = mbc;
    }

    public void update() {
        world.updateWorld();
        view.updateMinimap(world);
        view.updateLeaderboard(world);
        pc.update();
        entityView.updateView();
        mbc.getView().updateView();
        mbc.checkCollision();

        //freeQueuedObjects();





        //System.out.println(world.getEntities().get(0).getX() + " " + world.getEntities().get(0).getY());
    }

    public WorldModel getWorld() {
        return world;
    }
}
