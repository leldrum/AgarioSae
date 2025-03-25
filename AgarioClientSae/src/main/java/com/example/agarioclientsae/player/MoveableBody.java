package com.example.agarioclientsae.player;

import java.util.ArrayList;

import com.example.agarioclientsae.worldElements.Entity;
import com.example.agarioclientsae.app.HelloApplication;
import com.example.agarioclientsae.worldElements.World;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;


public abstract class MoveableBody extends Entity {

    public double Speed = 1.5; // self explanatory, the player's speed

    protected MoveableBody(Group group, double initialSize){
        super(group, initialSize);
    }

    public boolean checkCollision(){

        for(Node entity : HelloApplication.root.getChildren()){
            Entity collider = (Entity) entity;

            if (entity != this){

                Shape intersect = Shape.intersect(this.entity, collider.entity);

                if (intersect.getBoundsInLocal().getWidth() != -1){

                    double foodValue = 0.5;

                    if (isSmaller(collider.entity, this.entity)){
                        World.queueFree(collider);
                        foodValue += collider.entity.getRadius() / 20;
                        increaseSize(foodValue);
                    }
                    return true;
                }
            }
         }
        return false;
    }

    private Boolean isSmaller(Circle circleOne, Circle circleTwo){
        if (circleOne.getRadius() > circleTwo.getRadius() + 2){
            return false;
        }
        return true;
    }


    public void increaseSize(double foodValue){
        //called whenever the player eats food
        //once the player gets big enough, we want the camera to start zooming out
        entity.setRadius(entity.getRadius() + foodValue);
        setViewOrder(-entity.getRadius());

    }

    public void moveToward(double[] velocity) {

        //initalize velocity, which is the mouse position - player position
        velocity = new double[]{velocity[0] - entity.getCenterX(), velocity[1] - entity.getCenterY()};

        //used for the smooth movement depending on how far away the mouse is.
        //further away from the circle, the faster the movement is etc.
        double magnitudeSmoothing = Math.sqrt( (velocity[0] * velocity[0]) + (velocity[1] * velocity[1])) / getWeight();

        //limit speed of smoothing
        if (magnitudeSmoothing > 4){
            magnitudeSmoothing = 4 * Speed;
        }
        //normalize the position the player is going towards to get the direction
        velocity = normalizeDouble(velocity);

        //multiply direction by Speed to get the final velocity value, multiply smoothing for smoother movement
        velocity[0] *= Speed * magnitudeSmoothing;
        velocity[1] *= Speed * magnitudeSmoothing;

        //change Sprite position based on velocity
        //also check if the player is at the world limit, if it is then it doesnt move
        if (entity.getCenterX() + velocity[0] < World.getMapLimitWidth()){
            if (entity.getCenterX() + velocity[0] > -World.getMapLimitWidth()){
                entity.setCenterX(entity.getCenterX() + velocity[0] );
            }
        }
        if (entity.getCenterY() + velocity[1] < World.getMapLimitHeight()){
            if (entity.getCenterY() + velocity[1] > -World.getMapLimitHeight()){
                entity.setCenterY(entity.getCenterY() + velocity[1]);
            }
        }

    }


    public double distanceTo(double[] position){
        return Math.sqrt(Math.pow(position[0] - getPosition()[0], 2) - Math.pow(position[1] - getPosition()[1], 2) );
    }

    public double[] normalizeDouble(double[] array){
        //don't worry about it :)

        double magnitude = Math.sqrt( (array[0] * array[0]) + (array[1] * array[1]) );

        if (array[0] != 0 || array[1] != 0 ){
            return new double[]{array[0] / magnitude, array[1] / magnitude};
        }
        return new double[]{0,0};
    }


    public ArrayList<Entity> divide(){
        //if the player is big enough, divide the player into two
        if (entity.getRadius() > 20){

            int r = getR();
            int g  = getG();
            int b  = getB();

            //create a new player with half the size of the current player
            Entity newPlayer = new Player(HelloApplication.root, entity.getRadius() / 2);
            newPlayer.entity.setCenterX(entity.getCenterX());
            newPlayer.entity.setCenterY(entity.getCenterY());
            newPlayer.entity.setFill(Color.rgb(r, g , b, 0.99));

            //set the new player's position to the same as the current player
            entity.setRadius(entity.getRadius() / 2);
            return new ArrayList<Entity>(){{
                add(newPlayer);
            }};
        }
        return new ArrayList<Entity>(){};
    }
}
