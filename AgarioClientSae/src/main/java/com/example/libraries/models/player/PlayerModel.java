package com.example.libraries.models.player;

import java.util.Random;
import com.example.libraries.models.player.MoveableBodyModel;
import com.example.libraries.models.worldElements.WorldModel;


public class PlayerModel extends MoveableBodyModel implements IPlayerModel {

    public PlayerModel(double x, double y,double initialSize) {
        super(x, y, initialSize);
    }

    public void increaseSize(double foodValue) {
        setWeight(getWeight() + foodValue);
    }

    public double getWeight() {
        return super.getWeight();
    }

    public double getCenterX() {
        return super.getPosition()[0];
    }

    public double getCenterY() {
        return super.getPosition()[1];
    }

    public boolean GameOver = false;

    /*public double checkCollision() {
        double result = super.checkCollision();
        if (result != 0) {
            increaseSize(result);
            return result;
        }
        return 0;
    }*/

    public boolean isGameOver() {return GameOver;}

    @Override
    public void update() {

    }

    @Override
    public double totalRadius() {
        return 0;
    }


    public void checkCollision() {
    }

}
