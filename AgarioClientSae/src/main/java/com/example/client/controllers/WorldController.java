package com.example.client.controllers;

import com.example.client.views.EntityView;
import com.example.client.views.MoveableBodyView;
import com.example.libraries.models.worldElements.Entity;
import com.example.libraries.models.worldElements.WorldModel;
import com.example.libraries.views.WorldView;

import static com.example.client.app.HelloApplication.root;

public class WorldController {
    private WorldModel world;
    private WorldView view;

    private PlayableGroupController pc;

    private EntityView entityView;

    private MoveableBodyController mbc;

    public WorldController(WorldModel world, WorldView view, PlayableGroupController pc, EntityView entityView, MoveableBodyController mbc) {
        this.world = world;
        this.view = view;
        this.pc = pc;
        this.entityView = entityView;
        this.mbc = mbc;
    }

    public void freeQueuedObjects() {
        root.getChildren().removeAll(WorldModel.getInstance().getQueuedObjectsForDeletion());
        WorldModel.getInstance().clearQueudObjects();
    }

    public void update() {
        world.updateWorld();
        view.updateMinimap(world);
        view.updateLeaderboard(world);
        pc.update();
        entityView.updateView();
        mbc.getView().updateView();
        mbc.checkCollision();

        freeQueuedObjects();





        System.out.println(world.getEntities().get(0).getX() + " " + world.getEntities().get(0).getY());
    }

    public WorldModel getWorld() {
        return world;
    }
}
