package com.example.libraries.player;

import com.example.client.app.HelloApplication;
import com.example.libraries.worldElements.World;
import javafx.animation.ScaleTransition;
import javafx.scene.Group;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

public class PlayableGroup implements IPlayer{

    public ArrayList<Player> parts = new ArrayList<>();

    public CameraPlayer camera = new CameraPlayer(); // creates a camera for the player

    public double[] cameraScale = {camera.getScaleX(), camera.getScaleY()};

    private boolean canUpdate;

    public PlayableGroup(Group group, double initialSize, int groupP){
        parts.add(new Player(group, initialSize, groupP));
        canUpdate = true;
    }
    @Override
    public void increaseSize(double foodValue) {
        int i = 0;
        for(Player part : parts){
            part.increaseSize(foodValue);
            if(i % 2 == 0) {
                part.sprite.entity.setCenterX(part.sprite.entity.getCenterX() - foodValue / 2);
            }
            else{
                part.sprite.entity.setCenterX(part.sprite.entity.getCenterX() + foodValue / 2);
            }
            i++;
        }

        ScaleTransition cameraZoom = new ScaleTransition(Duration.millis(200), camera);
        if (totalRadius() > 70){
            cameraScale[0] += foodValue / 150;
            cameraScale[1] += foodValue / 150;
        }




        cameraZoom.setToX(cameraScale[0]);
        cameraZoom.setToY(cameraScale[1]);
        cameraZoom.play();
    }

    public double[] getPosition(){
        double posX = 0;
        double posY = 0;
        for(Player part : parts){
            posX += part.getPosition()[0];
            posY += part.getPosition()[1];
        }
        return new double[]{posX / parts.size(), posY / parts.size()};
    }

    @Override
    public double checkCollision() {
        if (parts.size() >= 1) {
            Iterator<Player> iterator = parts.iterator();
            while (iterator.hasNext()) {
                Player part = iterator.next();
                double result = part.checkCollision();
                if (result != 0 && result != 1) {
                    increaseSize(result);
                }
                if (result == 1) {
                    ScaleTransition cameraZoom = new ScaleTransition(Duration.millis(200), camera);

                    cameraScale[0] -= part.totalRadius() / 600;
                    cameraScale[1] -= part.totalRadius() / 600;

                    cameraZoom.setToX(cameraScale[0]);
                    cameraZoom.setToY(cameraScale[1]);
                    cameraZoom.play();
                    iterator.remove();

                    if (parts.size() < 1) {
                        System.out.println("gra");
                        HelloApplication.timer.stop();
                        gameOver();
                    }
                }
            }
        }
        return 0;
    }

    public double getSpeed(){
        return parts.get(0).sprite.Speed;
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
        velocity = new double[]{velocity[0] - getCenterX(), velocity[1] - getCenterY()};

        //used for the smooth movement depending on how far away the mouse is.
        //further away from the circle, the faster the movement is etc.
        double magnitudeSmoothing = Math.sqrt( (velocity[0] * velocity[0]) + (velocity[1] * velocity[1])) / parts.get(0).sprite.getWeight();

        //limit speed of smoothing
        if (magnitudeSmoothing > 4){
            magnitudeSmoothing = 4 * getSpeed();
        }
        //normalize the position the player is going towards to get the direction
        velocity = normalizeDouble(velocity);

        //multiply direction by Speed to get the final velocity value, multiply smoothing for smoother movement
        velocity[0] *= getSpeed() * magnitudeSmoothing;
        velocity[1] *= getSpeed() * magnitudeSmoothing;

        //change Sprite position based on velocity
        //also check if the player is at the world limit, if it is then it doesnt move
        if (getCenterX() + velocity[0] < World.getInstance().getMapLimitWidth()){
            if (getCenterX() + velocity[0] > -World.getInstance().getMapLimitWidth()){
                for(Player part : parts){
                    part.sprite.entity.setCenterX(part.getCenterX() + velocity[0] );
                }

            }
        }
        if (getCenterY() + velocity[1] < World.getInstance().getMapLimitHeight()){
            if (getCenterY() + velocity[1] > -World.getInstance().getMapLimitHeight()){
                for(Player part : parts){
                    part.sprite.entity.setCenterY(part.getCenterY() + velocity[1] );
                }
            }
        }
        camera.setLayoutX((getCenterX()) - HelloApplication.getScreenWidth() / 2 * camera.getScaleX());
        camera.setLayoutY((getCenterY())  - HelloApplication.getScreenHeight() / 2 * camera.getScaleY());
    }

    public double getWeight(){
        double total = 0;
        for(Player part : parts){
            total += part.sprite.getWeight();
        }
        return total;
    }

    public double[] normalizeDouble(double[] array){
        //don't worry about it :)

        double magnitude = Math.sqrt( (array[0] * array[0]) + (array[1] * array[1]) );

        if (array[0] != 0 || array[1] != 0 ){
            return new double[]{array[0] / magnitude, array[1] / magnitude};
        }
        return new double[]{0,0};
    }

    public void divide() {
        ArrayList<Player> newParts = new ArrayList<>();
        for (Player part : parts) {
            if (part.sprite.getWeight() < 50) {
                return;
            }
        }
        Iterator<Player> iterator = parts.iterator();
        while (iterator.hasNext()) {
            Player part = iterator.next();
            Player newPlayer = part.divide();
            if (newPlayer != null) {
                newParts.add(newPlayer);
                part.sprite.setWeight(parts.get(0).sprite.getWeight() / 2);
            }
        }
        parts.addAll(newParts);
        canUpdate = false;
        System.out.println(canUpdate);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                canUpdate = true;
            }
        }, 10000); // 10 secondes
    }

    public void Union(){
        if(canUpdate) {
            if(parts.size() > 1) {
                for (Player part : parts) {
                    part.sprite.setWeight(parts.get(0).sprite.getWeight() * 2);
                }
                Iterator<Player> iterator = parts.iterator();
                int i = 1;
                while (iterator.hasNext()) {
                    Player part = iterator.next();
                    if (parts.size() / 2 < i) {
                        iterator.remove();
                        part.sprite.entity.setVisible(false);
                    }
                    i++;
                }
            }
        }
    }

    public void gameOver(){
        World.getInstance().reset();
    }

    public CameraPlayer getCamera() {
        return camera;
    }
}
