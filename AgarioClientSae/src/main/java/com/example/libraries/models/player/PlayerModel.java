package com.example.libraries.models.player;

import java.util.Random;
import com.example.libraries.models.player.MoveableBodyModel;
import com.example.libraries.models.worldElements.WorldModel;


public class PlayerModel extends MoveableBodyModel implements IPlayerModel {

    public PlayerModel(double x, double y,double initialSize) {
        super(x, y, initialSize);
    }

    public void increaseSize(double foodValue) {
        this.weight += foodValue;
        super.setWeight(weight);
    }

    public double getWeight() {
        return weight;
    }

    public double getCenterX() {
        return super.getPosition()[0];
    }

    public double getCenterY() {
        return super.getPosition()[1];
    }

    /*@Override
    public void moveToward(double[] mousePos) {
        // Calculer la direction vers la souris
        double dx = mousePos[0] - this.getX();
        double dy = mousePos[1] - this.getY();

        // Normaliser la direction
        double magnitude = Math.sqrt(dx * dx + dy * dy);
        if (magnitude > 0) {
            dx /= magnitude;
            dy /= magnitude;
        }

        // Appliquer la vitesse
        dx *= speed;
        dy *= speed;

        // Modifier la position
        this.setX(this.getX() + dx);
        this.setY(this.getY() + dy);

    }*/




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


    public void checkCollision() {
    }

    public boolean isGameOver() {
        return false;
    }
}
