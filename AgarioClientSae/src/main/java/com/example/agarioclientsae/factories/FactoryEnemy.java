package com.example.agarioclientsae.factories;

import com.example.agarioclientsae.worldElements.Enemy;
import com.example.agarioclientsae.worldElements.World;
import javafx.scene.Group;
import javafx.scene.layout.Pane;

public class FactoryEnemy implements Factory<Enemy> {
    @Override
    public Enemy create(Pane gamePane, double weight) {
        World world = World.getInstance();
        Enemy enemy = new Enemy(gamePane, weight);
        world.addEntity(enemy);
        return enemy;
    }
}
