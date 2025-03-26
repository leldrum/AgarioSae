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


public class MoveableBody extends Entity {

    public double Speed = 1.5; // self explanatory, the player's speed


    protected MoveableBody(Group group, double initialSize, int id){
        super(group, initialSize, id);

    }

    public double checkCollision(){
        for(Node entity : HelloApplication.root.getChildren()){
            Entity collider = (Entity) entity;

            if (entity != this){

                Shape intersect = Shape.intersect(this.entity, collider.entity);

                if (intersect.getBoundsInLocal().getWidth() != -1){

                    double foodValue = 0.5;

                    if (isSmaller(collider.entity, this.entity)){
                        World.queueFree(collider);
                        foodValue += collider.entity.getRadius() / 20;
                        return foodValue;
                    }
                    System.out.println("Dead");
                    return 1;
                }
            }
         }
        return 0;
    }

    private Boolean isSmaller(Circle circleOne, Circle circleTwo){
        if (circleOne.getRadius() > circleTwo.getRadius() + 2){
            return false;
        }
        return true;
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

}
