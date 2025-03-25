package com.example.agarioclientsae.player;

import com.example.agarioclientsae.app.HelloApplication;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;


public class Player extends MoveableBody {

    private Pane cameraPane; // Le conteneur de la caméra
    private Pane miniMapPane; // Le conteneur pour la mini-carte
    private Group miniMapGroup; // Le groupe d'éléments affichés sur la mini-carte
    private DoubleProperty zoomFactor = new SimpleDoubleProperty(1); // Facteur de zoom
    private double[] cameraScale = {zoomFactor.get(), zoomFactor.get()};

    public Player(Pane gamePane, double initialSize) {
        super(gamePane, initialSize);
        // Initialisation de la caméra
        cameraPane = new Pane();
        cameraPane.setMouseTransparent(true);
        cameraPane.getChildren().add(entity); // Ajouter l'entité au conteneur de la caméra
        gamePane.getChildren().add(cameraPane);

        // Initialisation de la mini-carte
        miniMapPane = new Pane();
        miniMapPane.setPrefSize(200, 200); // Taille de la mini-carte
        miniMapPane.setStyle("-fx-border-color: black; -fx-border-width: 2;");
        gamePane.getChildren().add(miniMapPane); // Ajouter la mini-carte à l'écran

        // Positionner la mini-carte en bas à droite
        miniMapPane.setLayoutX(gamePane.getWidth() - 210); // Décalage de 10px
        miniMapPane.setLayoutY(gamePane.getHeight() - 210); // Décalage de 10px

        // Créer le groupe pour la mini-carte
        miniMapGroup = new Group();
        miniMapPane.getChildren().add(miniMapGroup);

        // Afficher une version réduite du joueur dans la mini-carte
        Circle miniMapPlayer = new Circle(entity.getRadius() / 10, Color.BLUE); // Mini-cercle pour le joueur
        miniMapGroup.getChildren().add(miniMapPlayer);

        entity.setCenterX(gamePane.getWidth() / 2);
        entity.setCenterY(gamePane.getHeight() / 2);
        entity.setViewOrder(-entity.getRadius());
    }

    // Méthode pour mettre à jour la mini-carte
    public void updateMiniMap() {
        // Réduire la position du joueur sur la mini-carte
        double miniMapX = entity.getCenterX() / HelloApplication.getScreenWidth() * miniMapPane.getWidth();
        double miniMapY = entity.getCenterY() / HelloApplication.getScreenHeight() * miniMapPane.getHeight();

        // Mettre à jour la position du mini-cercle sur la mini-carte
        miniMapGroup.getChildren().clear();
        Circle miniMapPlayer = new Circle(entity.getRadius() / 10, Color.BLUE); // Mini-cercle pour le joueur
        miniMapPlayer.setCenterX(miniMapX);
        miniMapPlayer.setCenterY(miniMapY);
        miniMapGroup.getChildren().add(miniMapPlayer);
    }

    // Déplacement et mise à jour de la caméra
    public void moveToward(double[] velocity) {
        super.moveToward(velocity);
        velocity = normalizeDouble(velocity);
        velocity[0] *= 10;  // Augmenter la vitesse
        velocity[1] *= 10;
        entity.setCenterX(entity.getCenterX() + velocity[0]);
        entity.setCenterY(entity.getCenterY() + velocity[1]);

        // Mettre à jour la position de la caméra
        cameraPane.setTranslateX(HelloApplication.getScreenWidth() / 2 - entity.getCenterX() * cameraScale[0]);
        cameraPane.setTranslateY(HelloApplication.getScreenHeight() / 2 - entity.getCenterY() * cameraScale[1]);

        // Mettre à jour la mini-carte
        updateMiniMap();
    }

    @Override
    public void Update() {
        moveToward(HelloApplication.getMousePosition());
        checkCollision();
    }
}
