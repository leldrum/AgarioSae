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
            minimapCanvas.setTranslateX(2000 - MINIMAP_SIZE - 20);
            minimapCanvas.setTranslateY(2000 - MINIMAP_SIZE - 20);
            minimapCanvas.setMouseTransparent(true); // Important pour les clics
        }
    }

    // Mise à jour de la minimap
    public void updateMinimap() {
        if (player == null || minimapCanvas == null) {
            createMinimap(); // Recreate minimap if it doesn't exist
            return;
        }

        // Récupérer la position et la taille du joueur
        double playerX = player.getPosition()[0];
        double playerY = player.getPosition()[1];
        double playerRadius = Math.sqrt(player.getWeight())*10; // Supposant que vous avez une méthode getRadius()

        // Calculer dynamiquement la taille de la minimap en fonction de la taille du joueur
        double dynamicMinimapSize = Math.max(MINIMAP_SIZE, playerRadius * 3); // Taille minimale de 150, sinon 3x le rayon du joueur

        // Recréer le canvas si nécessaire pour s'adapter à la nouvelle taille
        if (minimapCanvas.getWidth() != dynamicMinimapSize || minimapCanvas.getHeight() != dynamicMinimapSize) {
            minimapCanvas = new Canvas(dynamicMinimapSize, dynamicMinimapSize);
            minimapCanvas.setMouseTransparent(true);
            root.getChildren().add(minimapCanvas);
        }

        // Calculer la nouvelle position de la minimap par rapport au joueur
        double minimapX = playerX +1280/3;  // Déplacement en X (100 pixels à droite)
        double minimapY = playerY +720/5;  // Déplacement en Y (100 pixels en bas)

        // Mettre à jour la position de la minimap
        minimapCanvas.setTranslateX(minimapX);
        minimapCanvas.setTranslateY(minimapY);

        // Mise à jour du graphique de la minimap
        GraphicsContext gc = minimapCanvas.getGraphicsContext2D();
        gc.clearRect(0, 0, dynamicMinimapSize, dynamicMinimapSize);

        // Fond de la minimap semi-transparent
        gc.setFill(Color.rgb(200, 200, 200, 0.5));
        gc.fillRect(0, 0, dynamicMinimapSize, dynamicMinimapSize);
        gc.setStroke(Color.BLACK);
        gc.strokeRect(0, 0, dynamicMinimapSize, dynamicMinimapSize);

        // Calcul dynamique de l'échelle en fonction de la taille du joueur
        double scaleX = dynamicMinimapSize / mapLimitWidth;
        double scaleY = dynamicMinimapSize / mapLimitHeight;

        // Position du joueur sur la minimap
        double playerMiniX = player.getPosition()[0] * scaleX + dynamicMinimapSize / 2;
        double playerMiniY = player.getPosition()[1] * scaleY + dynamicMinimapSize / 2;

        // Dessin du joueur sur la minimap
        gc.setFill(Color.RED);
        gc.fillOval(playerMiniX - 3, playerMiniY - 3, 6, 6);
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