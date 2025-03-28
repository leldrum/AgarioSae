package com.example.client.views;

import com.example.client.app.HelloApplication;
import com.example.libraries.models.player.PlayerModel;
import javafx.animation.ScaleTransition;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import com.example.libraries.models.player.CameraPlayer;

public class PlayableGroupView extends Group {
    private CameraPlayer camera;
    private double[] cameraScale;

    public static PlayerModel player;


    public PlayableGroupView(PlayerModel player) {
        this.camera = new CameraPlayer();
        this.cameraScale = new double[]{camera.getScaleX(), camera.getScaleY()};
        this.player = player;
    }

    public void zoom(double factor) {
        System.out.println("FACTOR:" + factor);

        // Modifier directement l'échelle de la root
        double newScaleX = HelloApplication.root.getScaleX() - factor * 0.05;
        double newScaleY = HelloApplication.root.getScaleY() - factor * 0.05;

        // Empêcher un zoom trop petit ou trop grand
        //if (newScaleX < 0.5) newScaleX = 0.5;  // Limite de dézoom
        //if (newScaleX > 3) newScaleX = 3;      // Limite de zoom

        HelloApplication.root.setScaleX(newScaleX);
        HelloApplication.root.setScaleY(newScaleY);


        System.out.println("Zoom Level: " + newScaleX);
    }


    public void updateCameraPosition(double x, double y) {
        HelloApplication.root.setTranslateX(-x + HelloApplication.getScreenWidth() / 2);
        HelloApplication.root.setTranslateY(-y + HelloApplication.getScreenHeight() / 2);
        camera.setX(x);
        camera.setY(y);
        //System.out.println("X:" + camera.getX() +"Y:" + camera.getY());
    }

    public CameraPlayer getCamera() {
        return camera;
    }

    public PlayerModel getPlayer() {
        return player;
    }
}
