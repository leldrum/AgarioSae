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
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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
    private Canvas leaderboardCanvas;

    private World() {
        this.root = new Group();
        createMinimap();
        createLeaderboard();
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
            minimapCanvas.setTranslateX(mapLimitWidth - MINIMAP_SIZE - 20);
            minimapCanvas.setTranslateY(mapLimitHeight - MINIMAP_SIZE - 20);
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

        // Dessin du joueur
        gc.setFill(Color.RED);
        gc.fillOval(playerMiniX - 3, playerMiniY - 3, 6, 6);

        // Ensure minimap is in the root if it's not already
        if (!root.getChildren().contains(minimapCanvas)) {
            root.getChildren().add(minimapCanvas);
        }
    }

    public void createLeaderboard() {
        if (leaderboardCanvas == null) {
            leaderboardCanvas = new Canvas(200, 150);
            leaderboardCanvas.setMouseTransparent(true); // Pour ne pas interférer avec les autres éléments
            root.getChildren().add(leaderboardCanvas);  // Ajouter le canvas une seule fois au groupe
        }
    }

    public void updateLeaderboard() {

        GraphicsContext gc = leaderboardCanvas.getGraphicsContext2D();
        gc.clearRect(0, 0, leaderboardCanvas.getWidth(), leaderboardCanvas.getHeight());

        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, leaderboardCanvas.getWidth(), leaderboardCanvas.getHeight()); // Remplir le fond en noir

        gc.setFill(Color.WHITE);
        gc.setFont(new Font(16));
        gc.fillText("Top 5 Masses", 50, 20);

        // Liste des entités triées par masse
        List<Entity> massiveEntities = entities.stream()
                .filter(e -> !(e instanceof Food)) // Exclure les Food
                .sorted(Comparator.comparingDouble(Entity::getWeight).reversed()) // Tri décroissant par masse
                .limit(5)
                .toList();

        // Affichage des entités
        int yOffset = 40;
        for (int i = 0; i < massiveEntities.size(); i++) {
            Entity e = massiveEntities.get(i);
            String entityType = (e instanceof Player) ? "P" : "E"; // "P" pour joueur, "E" pour ennemi
            gc.fillText((i + 1) + ". " + entityType + " - " + (int) e.getWeight(), 20, yOffset);
            yOffset += 20;
        }

        // Calcul de la taille du Canvas en fonction de la masse du joueur
        double playerMass = player.getWeight(); // Masse du joueur
        double canvasWidth = 200 + playerMass / 2; // Par exemple, augmenter la largeur en fonction de la masse
        double canvasHeight = 150 + playerMass / 2; // De même pour la hauteur

        leaderboardCanvas.setWidth(canvasWidth);
        leaderboardCanvas.setHeight(canvasHeight);

        // Débogage de la position du joueur
        double playerX = player.getPosition()[0];
        double playerY = player.getPosition()[1];
        System.out.println("Player X: " + playerX + ", Player Y: " + playerY);

        // Calcul des coordonnées pour afficher le leaderboard en fonction de la position du joueur
        double canvasOffset = 400 + playerMass; // Espacement pour placer le leaderboard à droite du joueur
        double leaderboardX = playerX + canvasOffset;
        double leaderboardY = playerY - canvasOffset;

        // Affichage des coordonnées du leaderboard
        System.out.println("Leaderboard X: " + leaderboardX);
        System.out.println("Leaderboard Y: " + leaderboardY);

        // Mise à jour de la position du canvas
        leaderboardCanvas.setTranslateX(leaderboardX);
        leaderboardCanvas.setTranslateY(leaderboardY);
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
        updateLeaderboard();
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
