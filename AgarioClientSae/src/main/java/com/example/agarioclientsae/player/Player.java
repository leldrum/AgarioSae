package com.example.agarioclientsae.player;

import com.example.agarioclientsae.app.HelloApplication;
import com.example.agarioclientsae.worldElements.World;
import javafx.animation.ScaleTransition;
import javafx.scene.Group;
import javafx.util.Duration;

public class Player extends MoveableBody {

    public CameraPlayer camera = new CameraPlayer(); // creates a camera for the player

    public double[] cameraScale = {camera.getScaleX(), camera.getScaleY()};

    public Player(Group group, double initialSize) {
        super(group, initialSize);
        // Initialisation à la position du centre
        entity.setCenterX(0);
        entity.setCenterY(0);

        // Met le joueur devant les autres éléments
        entity.setViewOrder(-entity.getRadius());
    }

    @Override
    public void increaseSize(double foodValue) {
        super.increaseSize(foodValue);
        // Zoom de la caméra quand le joueur grossit

        ScaleTransition cameraZoom = new ScaleTransition(Duration.millis(200), camera);

        if (entity.getRadius() > 70) {
            cameraScale[0] += foodValue / 200;
            cameraScale[1] += foodValue / 200;
        }

        cameraZoom.setToX(cameraScale[0]);
        cameraZoom.setToY(cameraScale[1]);
        cameraZoom.play();
    }

    @Override
    public void moveToward(double[] velocity) {
        super.moveToward(velocity);
        velocity = normalizeDouble(velocity);
        // Positionne la caméra au centre de l'écran
        camera.setLayoutX((entity.getCenterX() + velocity[0]) - HelloApplication.getScreenWidth() / 2 * camera.getScaleX());
        camera.setLayoutY((entity.getCenterY() + velocity[1]) - HelloApplication.getScreenHeight() / 2 * camera.getScaleY());
    }

    public void gameOver() {
        World.queueFree(entity);
    }

    @Override
    public void Update() {
        // Ajoutez une vérification de nullité
        if (this.entity == null || this.entity.getScene() == null) {
            return;
        }

        moveToward(HelloApplication.getMousePosition());
        checkCollision();

        // Force la mise à jour de la minimap
        World.getInstance().updateMinimap();
    }
}