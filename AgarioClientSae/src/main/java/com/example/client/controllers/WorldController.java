package com.example.client.controllers;

import com.example.client.views.WorldView;
import com.example.libraries.models.worldElements.WorldModel;
import javafx.scene.Group;

public class WorldController extends Group {
    private WorldModel world;
    private WorldView view;

    public WorldController(WorldModel world, WorldView view) {
        this.world = world;
        this.view = view;
    }

    public void update() {
        world.updateWorld();
        view.updateMinimap(world);
        view.updateLeaderboard(world);
        System.out.println(world.getEntities().get(0).getX() + " " + world.getEntities().get(0).getY());


    }

    public WorldModel getWorld() {
        return world;
    }
}
