package com.example.client.views;

import javafx.animation.ScaleTransition;
import javafx.util.Duration;
import com.example.libraries.models.player.CameraPlayer;

public class PlayableGroupView {
    private CameraPlayer camera;
    private double[] cameraScale;

    public PlayableGroupView() {
        this.camera = new CameraPlayer();
        this.cameraScale = new double[]{camera.getScaleX(), camera.getScaleY()};
    }

    public void zoom(double factor) {
        ScaleTransition cameraZoom = new ScaleTransition(Duration.millis(200), camera);
        cameraScale[0] += factor;
        cameraScale[1] += factor;
        cameraZoom.setToX(cameraScale[0]);
        cameraZoom.setToY(cameraScale[1]);
        cameraZoom.play();
    }

    public void updateCameraPosition(double x, double y) {
        camera.setLayoutX(x);
        camera.setLayoutY(y);
    }

    public CameraPlayer getCamera() {
        return camera;
    }
}
