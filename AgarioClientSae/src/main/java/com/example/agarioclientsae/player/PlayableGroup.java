package com.example.agarioclientsae.player;

import com.example.agarioclientsae.app.HelloApplication;
import javafx.animation.ScaleTransition;
import javafx.scene.Group;
import javafx.util.Duration;

import java.util.ArrayList;

public class PlayableGroup implements IPlayer{

    public ArrayList<Player> parts = new ArrayList<>();

    public CameraPlayer camera = new CameraPlayer(); // creates a camera for the player

    public double[] cameraScale = {camera.getScaleX(), camera.getScaleY()};

    public PlayableGroup(Group group, double initialSize){
        parts.add(new Player(group, initialSize));
    }
    @Override
    public void increaseSize(double foodValue) {
        for(Player part : parts){
            part.increaseSize(foodValue);
        }


    }

    @Override
    public Boolean checkCollision() {
        for(Player part : parts){
            Boolean result = part.checkCollision();
            if (result){
                ScaleTransition cameraZoom = new ScaleTransition(Duration.millis(200), camera);
                if (totalRadius() > 70){
                    System.out.println("zooming out");
                    cameraScale[0] += 0.5 / 200;
                    cameraScale[1] += 0.5 / 200;
                }


                cameraZoom.setToX(cameraScale[0]);
                cameraZoom.setToY(cameraScale[1]);
                cameraZoom.play();
                return true;
            }
        }
        return false;
    }

    @Override
    public void Update() {

        moveToward(HelloApplication.getMousePosition());
        checkCollision();
    }

    @Override
    public double totalRadius() {
        double total = 0;
        for(Player part : parts){
            total += part.totalRadius();
        }
        return total;
    }

    @Override
    public double getCenterX() {
        double total = 0;
        for (Player part : parts) {
            total += part.getCenterX();
        }
        return total / parts.size();
    }

    @Override
    public double getCenterY() {
        double total = 0;
        for (Player part : parts) {
            total += part.getCenterY();
        }
        return total / parts.size();
    }

    @Override
    public void moveToward(double[] velocity) {
        for(Player part : parts){
            part.moveToward(velocity);
        }
        camera.setLayoutX((getCenterX()) - HelloApplication.getScreenWidth() / 2 * camera.getScaleX());
        camera.setLayoutY((getCenterY())  - HelloApplication.getScreenHeight() / 2 * camera.getScaleY());
    }

    public void divide(){

    }

    public CameraPlayer getCamera() {
        return camera;
    }
}
