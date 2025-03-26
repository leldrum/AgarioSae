
package com.example.agarioclientsae.player;

import com.example.agarioclientsae.app.HelloApplication;
import com.example.agarioclientsae.worldElements.World;
import javafx.animation.ScaleTransition;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Random;

public class Player implements IPlayer{

    public MoveableBody part;

    public Player(Group group, double initialSize){
        part = new MoveableBody(group, initialSize);
        Random rand = new Random();
        part.entity.setCenterX(0);
        part.entity.setCenterY(0);
        part.setViewOrder(-part.entity.getRadius());
    }

    public void increaseSize(double foodValue){
        part.entity.setRadius(part.entity.getRadius() + foodValue);
        part.setViewOrder(-part.entity.getRadius());
        //zoom out the camera when the player gets too big
    }

    public double getCenterX(){
        return part.entity.getCenterX();
    }

    public double getCenterY(){
        return part.entity.getCenterY();
    }

    public double totalRadius(){
        double total = 0;
        total += part.entity.getRadius();
        return total;
    }

    public Boolean checkCollision(){
        return part.checkCollision();
    }

    public void moveToward(double[] velocity) {
        part.moveToward(velocity);

        //set the position of the camera to the same position as the player
        //minus by half of the screen resolution, keep the player in the middle of the screen

    }

    @Override
    public Camera getCamera() {
        return null;
    }


    public void gameOver(){
        World.queueFree(part.entity);
    }


    public void Update(){
        //move player towards the mouse position
    }

}
