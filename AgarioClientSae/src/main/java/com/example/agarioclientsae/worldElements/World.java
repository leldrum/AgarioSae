package com.example.agarioclientsae.worldElements;

import com.example.agarioclientsae.factories.FactoryEnemy;
import com.example.agarioclientsae.factories.FactoryFood;
import com.example.agarioclientsae.player.MoveableBody;
import com.example.agarioclientsae.player.Player;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class World {
    private static final double mapLimitWidth = 2000;
    private static final double mapLimitHeight = 2000;
    private static final int MINIMAP_SIZE = 150;
    private static World instance = new World();

    private ArrayList<Entity> entities = new ArrayList<>();
    private Player player;
    private Canvas minimapCanvas;

    private int enemySpawnTimer = 100;
    private int enemySpawnRate = 100;
    public static int enemies = 0;

    private static ArrayList<Object> queuedObjectsForDeletion = new ArrayList<>();
    public int maxTimer = 2;
    public int timer = maxTimer;
    private QuadTree quadTree;
    private Group root;

    private World() {
        this.root = new Group();
        createMinimap();
    }

    public static World getInstance() {
        return instance;
    }

    public static Group getRoot() {
        return getInstance().root;
    }

    public Canvas getMinimapCanvas() {
        return this.minimapCanvas;
    }

    public Player getPlayer() {
        return this.player;
    }

    public void addPlayer(Player p) {
        player = p;
    }

    public static double getMapLimitWidth() {
        return mapLimitWidth;
    }

    public static double getMapLimitHeight() {
        return mapLimitHeight;
    }


    public void createMinimap() {
        if (minimapCanvas == null) {
            minimapCanvas = new Canvas(MINIMAP_SIZE, MINIMAP_SIZE);
            minimapCanvas.setTranslateX(1280 - MINIMAP_SIZE - 20);
            minimapCanvas.setTranslateY(720 - MINIMAP_SIZE - 20);
            minimapCanvas.setMouseTransparent(true); // Important pour les clics
        }
    }

    // Mise à jour de la minimap
    public void updateMinimap() {
        if (player == null || minimapCanvas == null) {
            createMinimap(); // Recreate minimap if it doesn't exist
            return;
        }

        GraphicsContext gc = minimapCanvas.getGraphicsContext2D();
        gc.clearRect(0, 0, MINIMAP_SIZE, MINIMAP_SIZE);

        // Fond de la minimap semi-transparent
        gc.setFill(Color.rgb(200, 200, 200, 0.5));
        gc.fillRect(0, 0, MINIMAP_SIZE, MINIMAP_SIZE);
        gc.setStroke(Color.BLACK);
        gc.strokeRect(0, 0, MINIMAP_SIZE, MINIMAP_SIZE);

        // Mise à l'échelle
        double scaleX = MINIMAP_SIZE / mapLimitWidth;
        double scaleY = MINIMAP_SIZE / mapLimitHeight;

        // Position du joueur sur la minimap
        double playerMiniX = player.getPosition()[0] * scaleX + MINIMAP_SIZE / 2;
        double playerMiniY = player.getPosition()[1] * scaleY + MINIMAP_SIZE / 2;

        // Dessin du joueur
        gc.setFill(Color.RED);
        gc.fillOval(playerMiniX - 3, playerMiniY - 3, 6, 6);

        // Ensure minimap is in the root if it's not already
        if (!root.getChildren().contains(minimapCanvas)) {
            root.getChildren().add(minimapCanvas);
        }
    }

    // Mise à jour du monde (appelée à chaque frame)
    public void Update() {
        if (timer <= 0) {
            if (root.getChildren().size() < 200) {
                createFood();
            }
            timer = maxTimer;
        }

        if (enemies < 5 && enemySpawnTimer <= 0) {
            FactoryEnemy factoryEnemy = new FactoryEnemy();
            Enemy enemy = factoryEnemy.create(root, 50);
            enemies++;
            enemySpawnTimer = enemySpawnRate;
        }

        enemySpawnTimer--;
        timer--;

        updateQuadTreeEntities();
        updateMinimap(); // Mise à jour de la minimap en temps réel
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
    }

    public void reset() {
        instance = new World();
    }

    public static void queueFree(Object object) {
        queuedObjectsForDeletion.add(object);
        Entity entity = (Entity) object;
        entity.onDeletion();
        enemies--;
    }

    public void freeQueuedObjects() {
        root.getChildren().removeAll(queuedObjectsForDeletion);
        queuedObjectsForDeletion.clear();
    }

    private void updateEntities() {
        for (Node entity : root.getChildren()) {
            if (entity instanceof MoveableBody) {
                MoveableBody moveableEntity = (MoveableBody) entity;
                moveableEntity.checkCollision();
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
