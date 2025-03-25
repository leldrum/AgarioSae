
package com.example.agarioclientsae.player;

import com.example.agarioclientsae.app.HelloApplication;
import com.example.agarioclientsae.worldElements.World;
import javafx.animation.ScaleTransition;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class Player extends MoveableBody{

    private Pane cameraPane; // Le conteneur de la caméra
    private DoubleProperty zoomFactor = new SimpleDoubleProperty(1); // Facteur de zoom
    private double[] cameraScale = {zoomFactor.get(), zoomFactor.get()};

    public Player(Pane gamePane, double initialSize){
        super(gamePane, initialSize);
        //new player made and added to the group
        cameraPane = new Pane();
        cameraPane.getChildren().add(entity); // Ajouter l'entité au conteneur de la caméra
        gamePane.getChildren().add(cameraPane);

        entity.setCenterX(0);
        entity.setCenterY(0);

        //puts the player infront of all the food
        entity.setViewOrder(-entity.getRadius());
    }

    public void increaseSize(double foodValue){
        super.increaseSize(foodValue);
        //zoom out the camera when the player gets too big

        ScaleTransition cameraZoom = new ScaleTransition(Duration.millis(200), cameraPane);

        if (entity.getRadius() > 70){
            cameraScale[0] += foodValue / 200;
            cameraScale[1] += foodValue / 200;
        }


        cameraZoom.setToX(cameraScale[0]);
        cameraZoom.setToY(cameraScale[1]);
        cameraZoom.play();

    }

    public void moveToward(double[] velocity) {
        super.moveToward(velocity);
        velocity = normalizeDouble(velocity);

        // Mettre à jour la position de la caméra pour qu'elle suive le joueur
        double cameraX = entity.getCenterX() + velocity[0] - HelloApplication.getScreenWidth() / 2 * cameraScale[0];
        double cameraY = entity.getCenterY() + velocity[1] - HelloApplication.getScreenHeight() / 2 * cameraScale[1];
        cameraPane.setTranslateX(-cameraX); // Appliquer le déplacement horizontal
        cameraPane.setTranslateY(-cameraY); // Appliquer le déplacement vertical
    }


    public void gameOver(){
        World.queueFree(entity);
    }

    @Override
    public void Update(){
        //move player towards the mouse position
        moveToward(HelloApplication.getMousePosition());

        //check if player is colliding with anything
        checkCollision();
    }

}
