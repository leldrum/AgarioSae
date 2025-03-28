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
        System.out.println();
        ScaleTransition cameraZoom = new ScaleTransition(Duration.millis(200), camera);
        cameraScale[0] += factor*100;
        cameraScale[1] += factor*100;
        cameraZoom.setToX(cameraScale[0]);
        cameraZoom.setToY(cameraScale[1]);
        cameraZoom.play();
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
