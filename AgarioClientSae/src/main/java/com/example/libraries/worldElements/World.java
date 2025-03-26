package com.example.libraries.worldElements;

import com.example.libraries.player.IPlayer;
import com.example.libraries.player.PlayableGroup;
import com.example.libraries.factories.FactoryEnemy;
import com.example.libraries.factories.FactoryFood;
import com.example.libraries.player.MoveableBody;
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

public class World implements Serializable {
    private final int MINIMAP_SIZE = 150;
    private static World instance = new World();

    private ArrayList<Entity> entities = new ArrayList<>();
    private transient Canvas minimapCanvas;

    private static final long serialVersionUID = 1L;
    private double mapLimitWidth = 2000;
    private double mapLimitHeight = 2000;

    private int enemySpawnTimer = 100;
    private int enemySpawnRate = 100;
    public int enemies = 0;

    private PlayableGroup player;

    private static ArrayList<Object> queuedObjectsForDeletion = new ArrayList<>();
    public int maxTimer = 2;
    public int timer = maxTimer;
    private transient QuadTree quadTree;
    private transient Group root;
    private transient Canvas leaderboardCanvas;

    private World() {
        this.root = new Group();
        createMinimap();
        createLeaderboard();
    }

    public static World getInstance() {
        return instance;
    }

    public Group getRoot() {
        return getInstance().root;
    }

    public Canvas getMinimapCanvas() {
        return this.minimapCanvas;
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

    public void createMinimap() {
        if (minimapCanvas == null) {
            minimapCanvas = new Canvas(MINIMAP_SIZE, MINIMAP_SIZE);
            minimapCanvas.setTranslateX(mapLimitWidth - MINIMAP_SIZE - 20);
            minimapCanvas.setTranslateY(mapLimitHeight - MINIMAP_SIZE - 20);
            minimapCanvas.setMouseTransparent(true); // Important pour les clics
        }
    }

    public void updateMinimap() {
        if (player == null || minimapCanvas == null) {
            createMinimap(); // Recreate minimap if it doesn't exist
            return;
        }

        double playerX = player.getCenterX();
        double playerY = player.getCenterY();
        double playerRadius = Math.sqrt(player.getWeight()) * 10;

        double dynamicMinimapSize = Math.max(MINIMAP_SIZE, playerRadius * 3);

        if (minimapCanvas.getWidth() != dynamicMinimapSize || minimapCanvas.getHeight() != dynamicMinimapSize) {
            root.getChildren().remove(minimapCanvas);
            minimapCanvas = new Canvas(dynamicMinimapSize, dynamicMinimapSize);
            minimapCanvas.setMouseTransparent(true);
            root.getChildren().add(minimapCanvas);
        }

        double minimapX = playerX + 1280 / 3;
        double minimapY = playerY + 720 / 5;

        minimapCanvas.setTranslateX(minimapX);
        minimapCanvas.setTranslateY(minimapY);

        GraphicsContext gc = minimapCanvas.getGraphicsContext2D();
        gc.clearRect(0, 0, dynamicMinimapSize, dynamicMinimapSize);

        gc.setFill(Color.rgb(200, 200, 200, 0.5));
        gc.fillRect(0, 0, dynamicMinimapSize, dynamicMinimapSize);
        gc.setStroke(Color.BLACK);
        gc.strokeRect(0, 0, dynamicMinimapSize, dynamicMinimapSize);

        double scaleX = dynamicMinimapSize / mapLimitWidth;
        double scaleY = dynamicMinimapSize / mapLimitHeight;

        double playerMiniX = player.getCenterX() * scaleX + dynamicMinimapSize / 2;
        double playerMiniY = player.getCenterY() * scaleY + dynamicMinimapSize / 2;

        gc.setFill(Color.RED);
        gc.fillOval(playerMiniX - 3, playerMiniY - 3, 6, 6);

        if (!root.getChildren().contains(minimapCanvas)) {
            root.getChildren().add(minimapCanvas);
        }
    }

    public void createLeaderboard() {
        if (leaderboardCanvas == null) {
            leaderboardCanvas = new Canvas(200, 150);
            leaderboardCanvas.setMouseTransparent(true);
            root.getChildren().add(leaderboardCanvas);
        }
    }

    public void updateLeaderboard() {
        GraphicsContext gc = leaderboardCanvas.getGraphicsContext2D();
        gc.clearRect(0, 0, leaderboardCanvas.getWidth(), leaderboardCanvas.getHeight());

        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, leaderboardCanvas.getWidth(), leaderboardCanvas.getHeight());

        gc.setFill(Color.WHITE);
        gc.setFont(new Font(16));
        gc.fillText("Top 5 Masses", 50, 20);

        List<Entity> massiveEntities = entities.stream()
                .filter(e -> !(e instanceof Food))
                .sorted(Comparator.comparingDouble(Entity::getWeight).reversed())
                .limit(5)
                .toList();

        int yOffset = 40;
        for (int i = 0; i < massiveEntities.size(); i++) {
            Entity e = massiveEntities.get(i);
            String entityType = (e instanceof IPlayer) ? "P" : "E";
            gc.fillText((i + 1) + ". " + entityType + " - " + (int) e.getWeight(), 20, yOffset);
            yOffset += 20;
        }

        double playerMass = player.getWeight();
        double canvasWidth = 200 + playerMass / 2;
        double canvasHeight = 150 + playerMass / 2;

        leaderboardCanvas.setWidth(canvasWidth);
        leaderboardCanvas.setHeight(canvasHeight);

        double playerX = player.getCenterX();
        double playerY = player.getCenterY();
        System.out.println("Player X: " + playerX + ", Player Y: " + playerY);

        double canvasOffset = 400 + playerMass;
        double leaderboardX = playerX + canvasOffset;
        double leaderboardY = playerY - canvasOffset;

        System.out.println("Leaderboard X: " + leaderboardX);
        System.out.println("Leaderboard Y: " + leaderboardY);

        leaderboardCanvas.setTranslateX(leaderboardX);
        leaderboardCanvas.setTranslateY(leaderboardY);
    }

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

        updateMinimap();
        updateLeaderboard();
        updateQuadTreeEntities();
    }

    public static void setInstance(World world) {
        instance = world;
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

    public void queueFree(Object object) {
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