package com.example.libraries.worldElements;

import com.example.libraries.options.Leaderboard;
import com.example.libraries.options.Minimap;
import com.example.libraries.options.NameGenerator;
import com.example.libraries.player.IPlayer;
import com.example.libraries.player.PlayableGroup;
import com.example.libraries.factories.FactoryEnemy;
import com.example.libraries.factories.FactoryFood;
import com.example.libraries.player.MoveableBody;
import com.example.libraries.player.Player;
import com.example.libraries.worldElements.SpecialFood;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class World implements Serializable {

    private static World instance = new World();

    private ArrayList<Entity> entities = new ArrayList<>();
    private static final long serialVersionUID = 1L;

    private double mapLimitWidth = 2000;
    private double mapLimitHeight = 2000;
    private int enemySpawnTimer = 100;
    private int enemySpawnRate = 100;
    public static int enemies = 0;
    public int enemiesMax;

    private PlayableGroup player;
    private static ArrayList<Object> queuedObjectsForDeletion = new ArrayList<>();

    public int maxTimer = 2;
    public int timer = maxTimer;
    private transient QuadTree quadTree;
    private static transient Group root;

    private transient Minimap minimap;
    private transient Leaderboard leaderboard;

    public World() {
        root = new Group();
        initializeUIElements();
    }

    public static World getInstance() {
        if (instance == null) {
            instance = new World();
        }
        return instance;
    }

    public static Group getRoot() {
        return getInstance().root;
    }

    public void setMapLimitHeight(double mapHeight) {
        this.mapLimitHeight = mapHeight;
    }

    public void setMapLimitWidth(double mapWidth) {
        this.mapLimitWidth = mapWidth;
    }

    public void setEnemiesMax(int enemies) {
        this.enemiesMax = enemies;
    }

    public PlayableGroup getPlayer() {
        return this.player;
    }

    public void addPlayer(PlayableGroup p) {
        player = p;
    }

    public double getMapLimitWidth() {
        return mapLimitWidth;
    }

    public double getMapLimitHeight() {
        return mapLimitHeight;
    }

    // Initialisation des éléments UI minimap et leaderboard
    private void initializeUIElements() {
        if (minimap == null) {
            minimap = new Minimap(root);
            System.out.println("✅ Minimap initialisée !");
        }
        if (leaderboard == null) {
            leaderboard = new Leaderboard(root);
            System.out.println("✅ Leaderboard initialisé !");
        }
    }

    // Correction de la désérialisation pour éviter que minimap et leaderboard soient null
    private Object readResolve() {
        System.out.println("♻️ readResolve() : Réinitialisation après désérialisation...");
        root = new Group();
        initializeUIElements();
        return this;
    }

    public void updateMinimap() {
        if (minimap != null && player != null) {
            minimap.updateMinimap(player);
        } else {
            System.out.println("⚠️ Erreur : minimap ou player est null.");
        }
    }

    public void updateLeaderboard() {
        if (leaderboard != null) {
            leaderboard.updateLeaderboard(player, entities);
        } else {
            System.out.println("⚠️ Erreur : leaderboard est null.");
        }
    }

    // Mise à jour du monde (appelée à chaque frame)
    public void Update() {
        if (timer <= 0) {
            if (root.getChildren().size() < 200 * (getMapLimitWidth() * getMapLimitHeight() / (2000 * 2000))) {
                createFood();
            }
            timer = maxTimer;
        }

        if (enemies < enemiesMax && enemySpawnTimer <= 0) {
            FactoryEnemy factoryEnemy = new FactoryEnemy();
            Enemy enemy = factoryEnemy.create(root, 50);
            enemy.setName(NameGenerator.generateRandomName());
            enemies++;
            enemySpawnTimer = enemySpawnRate;
        }

        enemySpawnTimer--;
        timer--;

        updateEnemy();
        updateMinimap();
        updateLeaderboard();
        updateQuadTreeEntities();
    }

    public static void setInstance(World world) {
        instance = world;
        instance.initializeUIElements();
    }

    public void addEntity(Entity entity) {
        entities.add(entity);
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }

    public void createFood() {
        FactoryFood factoryFood = new FactoryFood();
        Food food = factoryFood.create(root, 10);
        Random rand = new Random();

        if (rand.nextInt(6) == 0) {
            SpecialFood specialFood = new SpecialFood(root, 10, true, 1.5, 3000);
            specialFood.setName("Speed Power");
            addEntity(specialFood);
        }

        if (rand.nextInt(10) == 0) {
            SpecialFood specialFood = new SpecialFood(root, 15, false, 1.5, 3000);
            specialFood.setName("Division Power");
            addEntity(specialFood);
        }
    }

    public void reset() {
        instance = new World();
        instance.initializeUIElements();
    }

    public static void queueFree(Object object) {
        queuedObjectsForDeletion.add(object);
        if (object instanceof Entity entity) {
            entity.onDeletion();
        }
        enemies--;
    }

    public void freeQueuedObjects() {
        root.getChildren().removeAll(queuedObjectsForDeletion);
        queuedObjectsForDeletion.clear();
    }

    private void updateEnemy() {
        for (Node entity : root.getChildren()) {
            if (entity instanceof Enemy enemy) {
                enemy.checkCollision();
                enemy.Update();
            }
        }
    }

    private void updateQuadTreeEntities() {
        quadTree = new QuadTree(0, new Boundary(0, 0, (int) mapLimitWidth, (int) mapLimitHeight));
        for (Entity entity : entities) {
            int x = (int) entity.entity.getCenterX();
            int y = (int) entity.entity.getCenterY();
            quadTree.insert(x, y, entity);
        }
    }
}
