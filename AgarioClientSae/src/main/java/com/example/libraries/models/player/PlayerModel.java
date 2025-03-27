package com.example.libraries.models.player;

import java.util.Random;
import com.example.libraries.models.player.MoveableBodyModel;


public class PlayerModel extends MoveableBodyModel implements IPlayerModel {

    public PlayerModel(double x, double y,double initialSize) {
        super(x, y, initialSize);
    }

    public double[] getPosition() {
        return super.getPosition();
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

    @Override
    public void moveToward(double[] velocity) {

    }

    /*public double checkCollision() {
        double result = super.checkCollision();
        if (result != 0) {
            increaseSize(result);
            return result;
        }
        return 0;
    }*/

    @Override
    public void update() {

    }

    @Override
    public double totalRadius() {
        return 0;
    }

}
