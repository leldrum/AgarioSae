package com.example.libraries.models.worldElements;

import com.example.libraries.models.factories.FactoryEnemy;
import com.example.libraries.models.player.PlayableGroupModel;
import com.example.libraries.models.player.PlayerModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class WorldModel implements Serializable {
    private static WorldModel instance = new WorldModel();

    private List<Entity> entities = new ArrayList<>();
    private PlayableGroupModel player;

    private double mapWidth = 2000;
    private double mapHeight = 2000;
    private int enemySpawnTimer = 100;
    private int enemySpawnRate = 100;
    private int enemies = 0;

    private static final long serialVersionUID = 1L;

    private WorldModel() {}

    public static WorldModel getInstance() {
        return instance;
    }


    public PlayableGroupModel getPlayer() {
        return this.player;
    }

    public double getMapWidth() {
        return mapWidth;
    }

    public double getMapHeight() {
        return mapHeight;
    }

    public void addEntity(Entity entity) {
        entities.add(entity);
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public void updateWorld() {
        if (enemies < 5 && enemySpawnTimer <= 0) {
            FactoryEnemy factoryEnemy = new FactoryEnemy();

            Random rand = new Random();
            double x = rand.nextDouble() * mapWidth;
            double y = rand.nextDouble() * mapHeight;
            EnemyModel enemy = factoryEnemy.create(x,y, 50);
            entities.add(enemy);
            enemies++;
            enemySpawnTimer = enemySpawnRate;
        }
        enemySpawnTimer--;
    }

    public List<Entity> getTopEntities() {
        return entities.stream()
                .filter(e -> !(e instanceof Food))
                .sorted(Comparator.comparingDouble(Entity::getWeight).reversed())
                .limit(5)
                .toList();
    }
}
