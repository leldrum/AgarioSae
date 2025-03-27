package com.example.client.controllers;

import com.example.client.views.EnemyView;
import com.example.client.views.FoodView;
import com.example.client.views.PlayableGroupView;
import com.example.libraries.models.player.PlayerModel;
import com.example.libraries.models.worldElements.EnemyModel;
import com.example.libraries.models.worldElements.Entity;
import com.example.libraries.models.worldElements.Food;
import com.example.libraries.models.worldElements.WorldModel;
import com.example.libraries.views.WorldView;
import java.util.HashMap;
import java.util.Map;

import static com.example.client.app.HelloApplication.root;

public class WorldController {
    private WorldModel world;
    private WorldView view;

    private Map<EnemyModel, EnemyView> enemyViews = new HashMap<>();

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
            if (entity instanceof EnemyModel) {
                EnemyModel enemy = (EnemyModel) entity;

                // Vérifie si l'ennemi a déjà une vue associée dans la map
                if (!enemyViews.containsKey(enemy)) {
                    // Si l'ennemi n'a pas de vue, crée une nouvelle EnemyView et l'ajoute à la scène
                    EnemyView enemyView = new EnemyView(enemy);
                    root.getChildren().add(enemyView.getSprite());

                    // Ajoute la vue à la map
                    enemyViews.put(enemy, enemyView);
                } else {
                    // Si l'ennemi a déjà une vue, mets à jour sa position dans la scène
                    EnemyView existingEnemyView = enemyViews.get(enemy);
                    existingEnemyView.updateView();
                }
            }
        }
        //System.out.println(world.getEntities().get(0).getX() + " " + world.getEntities().get(0).getY());
    }

    public WorldModel getWorld() {
        return world;
    }
}
