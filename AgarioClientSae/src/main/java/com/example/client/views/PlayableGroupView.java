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

        cameraScale[0] = HelloApplication.root.getScaleX();
        cameraScale[1] = HelloApplication.root.getScaleY();


        cameraScale[0] = HelloApplication.root.getScaleX() - factor / 150;
        cameraScale[1] = HelloApplication.root.getScaleY() - factor / 150;

        // Empêcher un zoom trop petit ou trop grand
        //if (newScaleX < 0.5) newScaleX = 0.5;  // Limite de dézoom
        //if (newScaleX > 3) newScaleX = 3;      // Limite de zoom


        HelloApplication.root.setScaleX(cameraScale[0]);
        HelloApplication.root.setScaleY(cameraScale[1]);



        System.out.println("Zoom Level: " + cameraScale[0]);
    }


    public void updateCameraPosition(double x, double y) {

        double scaleRatioX = HelloApplication.root.getScaleX() ;
        double scaleRatioY = HelloApplication.root.getScaleY() ;

        HelloApplication.root.setTranslateX((-x + HelloApplication.getScreenWidth() / 2) * scaleRatioX);
        HelloApplication.root.setTranslateY((-y + HelloApplication.getScreenHeight() / 2) * scaleRatioY);

        //HelloApplication.root.setLayoutX((player.getX())- HelloApplication.getScreenWidth() / 2 * HelloApplication.root.getScaleX());
        //HelloApplication.root.setLayoutY((player.getY())  - HelloApplication.getScreenHeight() / 2 * HelloApplication.root.getScaleY());
        //camera.setX(x);
        //camera.setY(y);
        //System.out.println("X:" + camera.getX() +"Y:" + camera.getY());
    }

    public CameraPlayer getCamera() {
        return camera;
    }

    public PlayerModel getPlayer() {
        return player;
    }
}
