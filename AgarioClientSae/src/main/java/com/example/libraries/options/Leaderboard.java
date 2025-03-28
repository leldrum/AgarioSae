package com.example.libraries.options;

import com.example.libraries.worldElements.Enemy;
import com.example.libraries.worldElements.Entity;
import com.example.libraries.player.PlayableGroup;
import com.example.libraries.worldElements.Food;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.Group;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;

public class Leaderboard {
    private Canvas leaderboardCanvas;
    private Group root;

    public Leaderboard(Group root) {
        this.root = root;
        createLeaderboard();
    }

    private void createLeaderboard() {
        leaderboardCanvas = new Canvas(250, 150);
        leaderboardCanvas.setMouseTransparent(true);
        root.getChildren().add(leaderboardCanvas);
    }

    public void updateLeaderboard(PlayableGroup player, List<Entity> entities) {
        GraphicsContext gc = leaderboardCanvas.getGraphicsContext2D();
        gc.clearRect(0, 0, leaderboardCanvas.getWidth(), leaderboardCanvas.getHeight());

        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, leaderboardCanvas.getWidth(), leaderboardCanvas.getHeight()); // Remplir le fond en noir

        gc.setFill(Color.WHITE);
        gc.setFont(new Font(Math.min(20, 10 + player.getWeight() / 5)));
        gc.fillText("Top 10", 50, 20);

        // Liste des entités triées par masse (inclure le joueur dans le leaderboard)
        List<Entity> massiveEntities = new ArrayList<>(entities);
        massiveEntities.add(player.parts.get(0).part); // Ajouter le joueur AVANT le tri

        // Trier la liste par masse (du plus grand au plus petit)
        massiveEntities = massiveEntities.stream()
                .filter(e -> !(e instanceof Food)) // Exclure la nourriture
                .sorted(Comparator.comparingDouble(Entity::getWeight).reversed()) // Tri décroissant
                .limit(10)
                .toList();

        int yOffset = 40;
        for (int i = 0; i < massiveEntities.size(); i++) {
            Entity e = massiveEntities.get(i);

            String entityType = e instanceof Enemy ? e.getName() : "Joueur";
            gc.fillText((i + 1) + ". " + entityType + " - " + (int) e.getWeight(), 20, yOffset);
            yOffset += 20;
        }


        double playerMass = player.getWeight();
        double canvasWidth = Math.max(200, 200 + playerMass / 2);
        double canvasHeight = Math.max(200, 150 + playerMass / 2);

        leaderboardCanvas.setWidth(canvasWidth);
        leaderboardCanvas.setHeight(canvasHeight);

        // Calcul des coordonnées pour afficher le leaderboard en fonction de la position du joueur
        double playerX = player.getCenterX();
        double playerY = player.getCenterY();

        // Ajuster la position du leaderboard à droite du joueur
        double offsetX = 400 + playerMass;
        double offsetY = 350 + playerMass / 2;


        double leaderboardX = playerX + offsetX;
        double leaderboardY = playerY - offsetY;


        leaderboardCanvas.setTranslateX(leaderboardX);
        leaderboardCanvas.setTranslateY(leaderboardY);
        leaderboardCanvas.toFront();

    }

    public Canvas getCanvas() {
        return leaderboardCanvas;
    }
}
