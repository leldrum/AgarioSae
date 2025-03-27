package com.example.client.views;

import com.example.libraries.models.player.PlayerModel;
import javafx.animation.ScaleTransition;
import javafx.scene.Group;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import javafx.scene.paint.Color;
import com.example.libraries.models.player.CameraPlayer;

public class PlayableGroupView extends Group {
    private CameraPlayer camera;
    private double[] cameraScale;

    private PlayerModel player;

    private Circle circle;


    public PlayableGroupView(PlayerModel player) {
        this.camera = new CameraPlayer();
        this.cameraScale = new double[]{camera.getScaleX(), camera.getScaleY()};

        this.player = player;
        int r = player.getR();
        int g = player.getG();
        int b = player.getB();

        circle = new Circle(10 * Math.sqrt(player.getWeight()), Color.rgb(r, g, b, 0.99));
        circle.setCenterX(player.getX());
        circle.setCenterY(player.getY());

        getChildren().add(circle);
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
