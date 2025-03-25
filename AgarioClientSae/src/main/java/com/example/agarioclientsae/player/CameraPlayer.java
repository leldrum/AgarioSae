package com.example.agarioclientsae.player;

import com.example.agarioclientsae.worldElements.World;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.layout.Pane;

public class CameraPlayer {
    private DoubleProperty zoom = new SimpleDoubleProperty(1);
    private DoubleProperty offsetX = new SimpleDoubleProperty(0);
    private DoubleProperty offsetY = new SimpleDoubleProperty(0);

    private Player player;

    public CameraPlayer(Player player, Pane gamePane) {
        this.player = player;

        player.addWeight(10.0);
        // Zoom en fonction de la taille du joueur (ici, le poids ou le rayon)
        zoom.bind(Bindings.divide(50, player.weightProperty() ));

        // Décalage de la caméra pour suivre la position du joueur
        offsetX.bind(Bindings.multiply(player.entity.centerXProperty(), -1));
        offsetY.bind(Bindings.multiply(player.entity.centerYProperty(), -1));

        // Appliquer les bindings au monde
        World.getInstance().setCamera(zoom, offsetX, offsetY);

        // Appliquer un binding pour suivre la position du joueur dans le monde
        gamePane.translateXProperty().bind(offsetX);
        gamePane.translateYProperty().bind(offsetY);

        // Appliquer le zoom au groupe entier (ici, pour la vue du jeu)
        gamePane.scaleXProperty().bind(zoom);
        gamePane.scaleYProperty().bind(zoom);
    }

    public DoubleProperty zoomProperty() {
        return zoom;
    }

    public DoubleProperty offsetXProperty() {
        return offsetX;
    }

    public DoubleProperty offsetYProperty() {
        return offsetY;
    }

    public Player getPlayer() {
        return player;
    }
}
