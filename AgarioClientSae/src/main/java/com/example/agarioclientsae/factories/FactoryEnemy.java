package com.example.agarioclientsae.factories;

import com.example.agarioclientsae.worldElements.Enemy;
import com.example.agarioclientsae.worldElements.World;
import javafx.scene.Group;

public class FactoryEnemy implements Factory<Enemy> {
    @Override
    public Enemy create(Group group, double weight) {
        World world = World.getInstance();
        Enemy enemy = new Enemy(group, weight);
        world.addEntity(enemy);
        return enemy;
    }
}
