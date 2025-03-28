package com.example.client.views;

import com.example.libraries.models.player.IPlayerModel;
import com.example.libraries.models.worldElements.Entity;
import com.example.libraries.models.worldElements.Food;
import com.example.libraries.models.worldElements.WorldModel;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.example.client.app.HelloApplication.player;

public class WorldView {
    private Group root;
    private Canvas minimapCanvas;
    private Canvas leaderboardCanvas;
    private double mapLimitWidth = 2000;
    private double mapLimitHeight = 2000;
    private final int MINIMAP_SIZE = 150;
    private ArrayList<Entity> entities = new ArrayList<>();

    public WorldView(Group root) {
        this.root = root;
        this.minimapCanvas = new Canvas(150, 150);
        this.leaderboardCanvas = new Canvas(200, 150);
        root.getChildren().addAll(minimapCanvas, leaderboardCanvas);
    }

    public void createMinimap() {
        if (minimapCanvas == null) {
            minimapCanvas = new Canvas(MINIMAP_SIZE, MINIMAP_SIZE);
            minimapCanvas.setTranslateX(mapLimitWidth - MINIMAP_SIZE - 20);
            minimapCanvas.setTranslateY(mapLimitHeight - MINIMAP_SIZE - 20);
            minimapCanvas.setMouseTransparent(true); // Important pour les clics
        }
    }

    public void updateMinimap(WorldModel world) {
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
            String entityType = (e instanceof IPlayerModel) ? "P" : "E";
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

    public Group getRoot() {
        return root;
    }
}
