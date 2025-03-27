package com.example.client.controllers;

import com.example.libraries.models.worldElements.WorldModel;
import com.example.libraries.views.WorldView;

public class WorldController {
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
