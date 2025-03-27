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
        leaderboardCanvas = new Canvas(200, 150);
        leaderboardCanvas.setMouseTransparent(true);
        root.getChildren().add(leaderboardCanvas);
    }

    public void updateLeaderboard(PlayableGroup player, List<Entity> entities) {
        GraphicsContext gc = leaderboardCanvas.getGraphicsContext2D();
        gc.clearRect(0, 0, leaderboardCanvas.getWidth(), leaderboardCanvas.getHeight());

        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, leaderboardCanvas.getWidth(), leaderboardCanvas.getHeight()); // Remplir le fond en noir

        gc.setFill(Color.WHITE);
        gc.setFont(new Font(16));
        gc.fillText("Top 5 Masses", 50, 20);

        // Liste des entités triées par masse (inclure le joueur dans le leaderboard)
        List<Entity> massiveEntities = new ArrayList<>(entities);
        massiveEntities.add(player.parts.get(0).sprite); // Ajouter le joueur AVANT le tri

        // Trier la liste par masse (du plus grand au plus petit) et limiter à 5
        massiveEntities = massiveEntities.stream()
                .filter(e -> !(e instanceof Food)) // Exclure la nourriture
                .sorted(Comparator.comparingDouble(Entity::getWeight).reversed()) // Tri décroissant
                .limit(5)
                .toList();

        // Affichage du leaderboard
        int yOffset = 40;
        for (int i = 0; i < massiveEntities.size(); i++) {
            Entity e = massiveEntities.get(i);

            String entityType = e instanceof Enemy ? e.getName() : "Joueur";
            gc.fillText((i + 1) + ". " + entityType + " - " + (int) e.getWeight(), 20, yOffset);
            yOffset += 20;
        }

        // Calcul de la taille du Canvas en fonction de la masse du joueur
        double playerMass = player.getWeight(); // Masse du joueur (calculée via `getWeight()` de `PlayableGroup`)
        double canvasWidth = Math.max(200, 200 + playerMass / 2); // S'assurer que la largeur ne soit pas trop petite
        double canvasHeight = Math.max(150, 150 + playerMass / 2); // Idem pour la hauteur

        leaderboardCanvas.setWidth(canvasWidth);
        leaderboardCanvas.setHeight(canvasHeight);

        // Calcul des coordonnées pour afficher le leaderboard en fonction de la position du joueur
        double playerX = player.getCenterX();  // Position du joueur
        double playerY = player.getCenterY();  // Position du joueur

        // Ajuster la position du leaderboard à droite du joueur
        double offsetX = 400 + playerMass; // Ajuster l'espacement horizontal
        double offsetY = 100 + playerMass / 2; // Ajuster l'espacement vertical

        // S'assurer que le leaderboard ne se superpose pas au joueur
        double leaderboardX = playerX + offsetX;
        double leaderboardY = playerY - offsetY;

        // Mise à jour de la position du canvas
        leaderboardCanvas.setTranslateX(leaderboardX);
        leaderboardCanvas.setTranslateY(leaderboardY);
    }

    public Canvas getCanvas() {
        return leaderboardCanvas;
    }
}
