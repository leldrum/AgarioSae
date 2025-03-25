package com.example.agarioclientsae.worldElements;

import com.example.agarioclientsae.factories.FactoryEnemy;
import com.example.agarioclientsae.factories.FactoryFood;
import com.example.agarioclientsae.player.MoveableBody;
import com.example.agarioclientsae.player.Player;
import com.example.agarioclientsae.worldElements.observer.WorldObserver;
import javafx.beans.property.DoubleProperty;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class World {
    private static double mapLimitWidth = 1000;
    private static double mapLimitHeight = 1000;

    private int enemySpawnTimer = 100;
    private int enemySpawnRate = 100;

    public static Pane gamePane = new Pane();
    public static int enemies = 0;

    private ArrayList<Entity> entities = new ArrayList<>();
    private Player player;

    private static ArrayList<Object> queuedObjectsForDeletion = new ArrayList<>();

    public int maxTimer = 2;
    public int timer = maxTimer;

    private static World instance = new World();
    private QuadTree quadTree;

    // Liste des observateurs
    private List<WorldObserver> observers = new ArrayList<>();

    private World() {}

    public static double getMapLimitWidth() { return mapLimitWidth; }
    public static double getMapLimitHeight() { return mapLimitHeight; }

    public static World getInstance() { return instance; }
    public static Pane getRoot() { return gamePane; }

    public Player getPlayer() { return this.player; }
    public void addPlayer(Player p) { player = p; }

    public void addEntity(Entity entity) { entities.add(entity); }

    public ArrayList<Entity> getEntities() { return entities; }

    public void createFood() {
        FactoryFood factoryFood = new FactoryFood();
        Food food = factoryFood.create(gamePane, 10);
        System.out.println("Food créé : " + food);
    }

    public void reset() { instance = new World(); }

    public static void queueFree(Object object) {
        queuedObjectsForDeletion.add(object);
        Entity entity = (Entity) object;
        entity.onDeletion();
        enemies--;
    }

    public void freeQueuedObjects() {
        gamePane.getChildren().removeAll(queuedObjectsForDeletion);
        queuedObjectsForDeletion.clear();
    }

    // 🔥 Ajout de la gestion des observateurs
    public void addObserver(WorldObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(WorldObserver observer) {
        observers.remove(observer);
    }

    // 🔥 Mettre à jour et notifier les observateurs des entités absorbées
    public void Update() {
        if (timer <= 0) {
            if (gamePane.getChildren().size() < 200) {
                createFood();
            }
            timer = maxTimer;
        }

        if (enemies < 5 && enemySpawnTimer <= 0) {
            FactoryEnemy factoryEnemy = new FactoryEnemy();
            Enemy enemy = factoryEnemy.create(gamePane, 50);
            enemies++;
            enemySpawnTimer = enemySpawnRate;
        }

        enemySpawnTimer--;
        timer--;

        updateQuadTreeEntities();

        // Détecter les entités absorbées
        HashMap<Player, List<Entity>> absorbedEntities = detectAbsorptions();

        // Notifier les observateurs
        notifyObservers(absorbedEntities);
    }

    void updateQuadTreeEntities() {
        quadTree = new QuadTree(0, new Boundary(0, 0, (int) mapLimitWidth, (int) mapLimitHeight));

        for (Entity entity : entities) {
            int x = (int) entity.entity.getCenterX();
            int y = (int) entity.entity.getCenterY();
            quadTree.insert(x, y, entity);
        }
    }

    public void setCamera(DoubleProperty zoom, DoubleProperty offsetX, DoubleProperty offsetY) {
        gamePane.scaleXProperty().bind(zoom);
        gamePane.scaleYProperty().bind(zoom);
        gamePane.translateXProperty().bind(offsetX);
        gamePane.translateYProperty().bind(offsetY);
    }

    // 🔥 Détection des absorptions
    private HashMap<Player, List<Entity>> detectAbsorptions() {
        HashMap<Player, List<Entity>> absorbedEntities = new HashMap<>();

        for (Node entity : gamePane.getChildren()) {
            if (entity instanceof MoveableBody) {
                MoveableBody moveableEntity = (MoveableBody) entity;
                Player absorber = moveableEntity.checkCollisionAndAbsorb(); // Implémente cette méthode

                if (absorber != null) {
                    absorbedEntities.putIfAbsent(absorber, new ArrayList<>());
                    absorbedEntities.get(absorber).add((Entity) entity);
                    queueFree(entity);
                }
            }
        }

        return absorbedEntities;
    }

    // 🔥 Notifier tous les observateurs
    private void notifyObservers(HashMap<Player, List<Entity>> absorbedEntities) {
        for (WorldObserver observer : observers) {
            observer.onEntitiesAbsorbed(absorbedEntities);
        }
    }
}
