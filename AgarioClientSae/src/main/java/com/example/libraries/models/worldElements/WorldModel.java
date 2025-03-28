package com.example.libraries.models.worldElements;

import com.example.client.app.HelloApplication;
import com.example.client.views.EntitiesView;
import com.example.libraries.models.factories.FactoryEnemy;
import com.example.libraries.models.factories.FactoryFood;
import com.example.libraries.models.factories.FactoryPlayer;
import com.example.libraries.models.player.PlayableGroupModel;

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

    private int maxFood = 200; // Nombre max de pastilles sur la map
    private int foodSpawnRate = 10; // Combien de ticks entre chaque spawn
    private int foodSpawnTimer = foodSpawnRate;

    private ArrayList<Entity> queuedObjectsForDeletion = new ArrayList<>();

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

    public void spawnFood(int count) {
        Random rand = new Random();
        for (int i = 0; i < count; i++) {
            double x = (rand.nextDouble() * 2 - 1) * mapWidth;
            double y = (rand.nextDouble() * 2 - 1) * mapHeight;

            double size = 10;

            /*FactoryFood factoryFood = new FactoryFood();
            Food food = factoryFood.create(x, y, size);*/

            Food food = new Food(x,y,size);

            entities.add(food);
            //System.out.println("x:" + x +";" + y);
        }
    }


    public List<Entity> getEntities() {
        return entities;
    }

    public void updateWorld() {
        for (Entity entity : entities) {
            if (entity instanceof EnemyModel) {
                ((EnemyModel) entity).update(entities); // Met à jour la position de chaque ennemi
            }
        }

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

        if (entities.stream().filter(e -> e instanceof Food).count() < maxFood && foodSpawnTimer <= 0) {
            System.out.println("apagnan");
            spawnFood(40);
            foodSpawnTimer = foodSpawnRate;
        }
        foodSpawnTimer--;
    }

    public void queueFree(Entity entity) {
        queuedObjectsForDeletion.add(entity);
        entity.onDeletion();
        enemies--;
    }

    public ArrayList<Entity> getQueuedObjectsForDeletion() {
        return queuedObjectsForDeletion;
    }

    public void clearQueudObjects() {
        entities.removeAll(queuedObjectsForDeletion);
        queuedObjectsForDeletion.clear();
    }

    public List<Entity> getTopEntities() {
        return entities.stream()
                .filter(e -> !(e instanceof Food))
                .sorted(Comparator.comparingDouble(Entity::getWeight).reversed())
                .limit(5)
                .toList();
    }
}
