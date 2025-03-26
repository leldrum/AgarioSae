package com.example.libraries.options;

import com.example.libraries.worldElements.World;
import com.example.libraries.player.PlayableGroup;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.Group;

public class Minimap {
    private static final int MINIMAP_SIZE = 150;
    private Canvas minimapCanvas;
    private Group root;

    public Minimap(Group root) {
        this.root = root;
        createMinimap();
    }

    private void createMinimap() {
        minimapCanvas = new Canvas(MINIMAP_SIZE, MINIMAP_SIZE);
        minimapCanvas.setMouseTransparent(true); // Empêche les clics dessus
        root.getChildren().add(minimapCanvas);
    }

    public void updateMinimap(PlayableGroup player) {
        if (player == null) return;

        double playerX = player.getCenterX();
        double playerY = player.getCenterY();
        double playerRadius = Math.sqrt(player.getWeight()) * 10;
        double dynamicMinimapSize = Math.max(MINIMAP_SIZE, playerRadius * 3);

        // Redimensionner si nécessaire
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

        double scaleX = dynamicMinimapSize / World.getMapLimitWidth();
        double scaleY = dynamicMinimapSize / World.getMapLimitHeight();

        double playerMiniX = player.getCenterX() * scaleX + dynamicMinimapSize / 2;
        double playerMiniY = player.getCenterY() * scaleY + dynamicMinimapSize / 2;

        gc.setFill(Color.RED);
        gc.fillOval(playerMiniX - 3, playerMiniY - 3, 6, 6);
    }

    public Canvas getCanvas() {
        return minimapCanvas;
    }
}
