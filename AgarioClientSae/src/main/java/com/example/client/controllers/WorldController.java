package com.example.client.controllers;

import com.example.client.views.FoodView;
import com.example.libraries.models.worldElements.Entity;
import com.example.libraries.models.worldElements.Food;
import com.example.libraries.models.worldElements.WorldModel;
import com.example.libraries.views.WorldView;

import static com.example.client.app.HelloApplication.root;

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
        for (Entity entity : world.getEntities()) {
            if (entity instanceof Food) {
                boolean alreadyExists = root.getChildren().stream().anyMatch(node -> node instanceof FoodView);
                if (!alreadyExists) {
                    FoodView foodView = new FoodView((Food) entity);
                    root.getChildren().add(foodView);
                }
            }
        }
        System.out.println(world.getEntities().get(0).getX() + " " + world.getEntities().get(0).getY());
    }

    public WorldModel getWorld() {
        return world;
    }
}
