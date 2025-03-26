package com.example.agarioclientsae;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.List;

public class Minimap {
    private Canvas canvas;
    private double mapWidth;
    private double mapHeight;
    private double scaleFactor;  // Facteur d'échelle pour adapter la carte

    public Minimap(double width, double height, double worldSize) {
        this.mapWidth = width;
        this.mapHeight = height;
        this.scaleFactor = width / worldSize; // Échelle de la minimap
        this.canvas = new Canvas(width, height);
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public void update(World world, Player player) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, mapWidth, mapHeight);

        // Dessiner le fond de la minimap
        gc.setFill(Color.LIGHTGRAY);
        gc.fillRect(0, 0, mapWidth, mapHeight);

        // Dessiner les pastilles
        gc.setFill(Color.GREEN);
        for (Entity entity : world.getEntities()) {
            if (entity instanceof Food) {
                drawEntity(gc, entity, Color.GREEN);
            }
        }

        // Dessiner les autres joueurs
        gc.setFill(Color.RED);
        for (Entity entity : world.getEntities()) {
            if (entity instanceof Enemy) {
                drawEntity(gc, entity, Color.RED);
            }
        }

        // Dessiner le joueur en bleu
        drawEntity(gc, player, Color.BLUE);
    }

    private void drawEntity(GraphicsContext gc, Entity entity, Color color) {
        double x = entity.getPosition()[0] * scaleFactor;
        double y = entity.getPosition()[1] * scaleFactor;
        double size = entity.getSize() * scaleFactor;

        gc.setFill(color);
        gc.fillOval(x, y, size, size);
    }
}
