package com.example.libraries.views;

import com.example.libraries.models.worldElements.Entity;
import com.example.libraries.models.worldElements.Food;
import com.example.libraries.models.worldElements.WorldModel;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;

import java.util.List;

public class WorldView {
    private Group root;
    private Canvas minimapCanvas;
    private Canvas leaderboardCanvas;

    public WorldView(Group root) {
        this.root = root;
        this.minimapCanvas = new Canvas(150, 150);
        this.leaderboardCanvas = new Canvas(200, 150);
        root.getChildren().addAll(minimapCanvas, leaderboardCanvas);
    }

    public void updateMinimap(WorldModel world) {
        GraphicsContext gc = minimapCanvas.getGraphicsContext2D();
        gc.clearRect(0, 0, minimapCanvas.getWidth(), minimapCanvas.getHeight());
        gc.setFill(Color.GRAY);
        gc.fillRect(0, 0, minimapCanvas.getWidth(), minimapCanvas.getHeight());

        if (world.getPlayer() != null) {
            double px = world.getPlayer().getPosition()[0] * minimapCanvas.getWidth() / world.getMapWidth();
            double py = world.getPlayer().getPosition()[1] * minimapCanvas.getHeight() / world.getMapHeight();
            gc.setFill(Color.RED);
            gc.fillOval(px - 3, py - 3, 6, 6);
        }
    }

    public void updateLeaderboard(WorldModel world) {
        GraphicsContext gc = leaderboardCanvas.getGraphicsContext2D();
        gc.clearRect(0, 0, leaderboardCanvas.getWidth(), leaderboardCanvas.getHeight());
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, leaderboardCanvas.getWidth(), leaderboardCanvas.getHeight());

        gc.setFill(Color.WHITE);
        gc.setFont(new Font(16));
        gc.fillText("Top 5 Players", 50, 20);

        List<Entity> topEntities = world.getTopEntities();
        int yOffset = 40;
        for (int i = 0; i < topEntities.size(); i++) {
            Entity e = topEntities.get(i);
            gc.fillText((i + 1) + ". " + (int) e.getWeight(), 20, yOffset);
            yOffset += 20;
        }
    }

    public void update(List<Entity> entities) {
        root.getChildren().clear();

        for (Entity entity : entities) {
            if (entity instanceof Food) {
                Circle foodCircle = new Circle(entity., Color.GREEN);
                foodCircle.setCenterX(entity.entity.getCenterX());
                foodCircle.setCenterY(entity.entity.getCenterY());
                root.getChildren().add(foodCircle);
            }
            // Ajoutez des cas similaires pour les autres types d'entités
        }
    }

    public Group getRoot() {
        return root;
    }
}
