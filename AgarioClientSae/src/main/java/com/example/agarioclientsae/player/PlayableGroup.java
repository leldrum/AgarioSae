package com.example.agarioclientsae.player;

import com.example.agarioclientsae.app.HelloApplication;
import com.example.agarioclientsae.app.MenuStart;
import com.example.agarioclientsae.worldElements.World;
import javafx.animation.ScaleTransition;
import javafx.scene.Group;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;

public class PlayableGroup implements IPlayer{

    public ArrayList<Player> parts = new ArrayList<>();

    public CameraPlayer camera = new CameraPlayer(); // creates a camera for the player

    public double[] cameraScale = {camera.getScaleX(), camera.getScaleY()};

    public PlayableGroup(Group group, double initialSize, int id){
        parts.add(new Player(group, initialSize, id));
    }
    @Override
    public void increaseSize(double foodValue) {
        int i = 0;
        for(Player part : parts){
            part.increaseSize(foodValue);
            if(i % 2 == 0) {
                part.part.entity.setCenterX(part.part.entity.getCenterX() - foodValue);
            }
            else{
                part.part.entity.setCenterX(part.part.entity.getCenterX() + foodValue);
            }
            i++;
        }

        ScaleTransition cameraZoom = new ScaleTransition(Duration.millis(200), camera);
        if (totalRadius() > 70){
            System.out.println("zooming out");
            cameraScale[0] += foodValue / 100;
            cameraScale[1] += foodValue / 100;
        }




        cameraZoom.setToX(cameraScale[0]);
        cameraZoom.setToY(cameraScale[1]);
        cameraZoom.play();
    }

    @Override
    public double checkCollision() {
        for(Player part : parts){
            double result = part.checkCollision();
            if(result != 0){
                increaseSize(result);
            }
            if (result == 1){
                parts.remove(part);
            }
        }
        return 0;
    }

    public double getSpeed(){
        return parts.get(0).part.Speed;
    }

    @Override
    public void Update() {
        moveToward(HelloApplication.getMousePosition());
        checkCollision();
        if(parts.size() < 1){
            gameOver();
        }
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
        double magnitudeSmoothing = Math.sqrt( (velocity[0] * velocity[0]) + (velocity[1] * velocity[1])) / parts.get(0).part.getWeight();

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
        if (getCenterX() + velocity[0] < World.getMapLimitWidth()){
            if (getCenterX() + velocity[0] > -World.getMapLimitWidth()){
                for(Player part : parts){
                    part.part.entity.setCenterX(part.getCenterX() + velocity[0] );
                }

            }
        }
        if (getCenterY() + velocity[1] < World.getMapLimitHeight()){
            if (getCenterY() + velocity[1] > -World.getMapLimitHeight()){
                for(Player part : parts){
                    part.part.entity.setCenterY(part.getCenterY() + velocity[1] );
                }
            }
        }
        camera.setLayoutX((getCenterX()) - HelloApplication.getScreenWidth() / 2 * camera.getScaleX());
        camera.setLayoutY((getCenterY())  - HelloApplication.getScreenHeight() / 2 * camera.getScaleY());
    }

    public double getTotalWeight(){
        double total = 0;
        for(Player part : parts){
            total += part.part.getWeight();
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
        if (parts.size() == 1){
            Player newPlayer = new Player(HelloApplication.root, parts.get(0).part.getWeight() / 2, parts.get(0).part.getId_());
            newPlayer.part.entity.setCenterX(parts.get(0).part.entity.getCenterX() + parts.get(0).part.entity.getRadius()*2 + 1);
            newPlayer.part.entity.setCenterY(parts.get(0).part.entity.getCenterY());
            parts.add(newPlayer);
            parts.get(0).part.setWeight(parts.get(0).part.getWeight() / 2);
        }
        /*if (parts.get(0).part.getWeight() > 10){
            int nbParts = parts.size();
            for (int i = 0; i < nbParts; i++) {
                Player originalPlayer = this.parts.get(i);
                Player newPlayer = new Player(HelloApplication.root, originalPlayer.part.getWeight() /2);

                this.parts.get(i).part.setWeight(originalPlayer.part.getWeight() / 2);
                this.parts.get(i).part.entity.setRadius(originalPlayer.part.entity.getRadius() / 2);

                newPlayer.part.entity.setRadius(originalPlayer.part.entity.getRadius());
                if((nbParts / 2) % 2 == 0) {
                    this.parts.get(i).part.entity.setCenterX(originalPlayer.part.entity.getCenterX() - totalRadius() + 1);
                    newPlayer.part.entity.setCenterX(originalPlayer.part.entity.getCenterX() + totalRadius() + 1);
                    newPlayer.part.entity.setCenterY(originalPlayer.part.entity.getCenterY());
                }
                else{
                    newPlayer.part.entity.setCenterX(originalPlayer.part.entity.getCenterX());
                    newPlayer.part.entity.setCenterY(originalPlayer.part.entity.getCenterY() + totalRadius() + 1);
                }

                this.parts.add(newPlayer);

                this.parts.get(i).part.setViewOrder(-this.parts.get(i).part.entity.getRadius());

                newPlayer.part.setViewOrder(-newPlayer.part.entity.getRadius());
            }
        }*/
    }

    public void gameOver(){
        World.getInstance().reset();
        HelloApplication.scene.setRoot(new MenuStart(new Stage()));
    }

    public CameraPlayer getCamera() {
        return camera;
    }
}
