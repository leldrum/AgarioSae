package com.example.client.controllers;

import com.example.client.views.PlayableGroupView;
import com.example.libraries.models.player.MoveableBodyModel;
import com.example.libraries.models.player.PlayerModel;
import com.example.libraries.models.worldElements.EnemyModel;
import com.example.libraries.models.worldElements.Entity;
import com.example.libraries.models.worldElements.Food;
import com.example.client.views.EntitiesView;
import com.example.client.views.WorldView;
import com.example.libraries.models.worldElements.WorldModel;
import com.example.client.views.WorldView;
import java.util.HashMap;
import java.util.Map;

import static com.example.client.app.HelloApplication.player;
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
        view.updateLeaderboard();
        pc.update();
        entityView.updateView();
        pc.getView().zoom(mbc.checkCollision(player));

        //freeQueuedObjects();
        //System.out.println(world.getEntities().get(0).getX() + " " + world.getEntities().get(0).getY());
        for (Entity entity : world.getEntities()) {
            if(entity instanceof MoveableBodyModel && !(entity.equals(player))) {
                mbc.checkCollision((MoveableBodyModel) entity);
            }
            if (entity instanceof EnemyModel) {
                EnemyModel enemy = (EnemyModel) entity;
                System.out.println(enemy.getStrategy());

                // Vérifie si l'ennemi a déjà une vue associée dans la map
                if (!entityView.getEntityGraphicsMap().containsKey(enemy)) {
                    entityView.addEntityGraphics(enemy);
                }
            }
        }


        //System.out.println(world.getEntities().get(0).getX() + " " + world.getEntities().get(0).getY());
    }

    public WorldModel getWorld() {
        return world;
    }
}
