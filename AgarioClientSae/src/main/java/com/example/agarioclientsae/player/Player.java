
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

    public Player(Group group, double initialSize, int id){
        part = new MoveableBody(group, initialSize, id);
        Random rand = new Random();
        part.entity.setCenterX(0);
        part.entity.setCenterY(0);
        part.setViewOrder(-part.entity.getRadius());
    }



    public void increaseSize(double foodValue){
        part.setWeight(part.getWeight() + foodValue);
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

    public double checkCollision(){
        double result = part.checkCollision();
        if(result != 0 && result != 1){
            increaseSize(result);
            return result;
        }
        return 0;
    }

    public void moveToward(double[] velocity) {}

    @Override
    public Camera getCamera() {
        return null;
    }


    public void Update(){
        //move player towards the mouse position
    }

}
