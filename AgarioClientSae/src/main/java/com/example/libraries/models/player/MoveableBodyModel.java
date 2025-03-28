package com.example.libraries.models.player;

import com.example.client.app.HelloApplication;
import com.example.libraries.models.worldElements.Entity;
import com.example.libraries.models.worldElements.WorldModel;
import org.w3c.dom.Node;

public class MoveableBodyModel extends Entity {

    protected double speed = 1.5;

    public MoveableBodyModel(double startX, double startY, double weight) {
        super(startX, startY, weight);
    }

    public void moveToward(double[] velocity) {

        //initalize velocity, which is the mouse position - player position
        velocity = new double[]{velocity[0] - this.getX(), velocity[1] - this.getY()};

        //used for the smooth movement depending on how far away the mouse is.
        //further away from the circle, the faster the movement is etc.
        double magnitudeSmoothing = Math.sqrt( (velocity[0] * velocity[0]) + (velocity[1] * velocity[1])) / getWeight();

        //limit speed of smoothing

        if (magnitudeSmoothing < 0.1) magnitudeSmoothing = 0.1;

        if (magnitudeSmoothing > 4){
            magnitudeSmoothing = 4 * speed;
        }
        //normalize the position the player is going towards to get the direction
        velocity = normalizeDouble(velocity);

        //multiply direction by Speed to get the final velocity value, multiply smoothing for smoother movement
        velocity[0] *= speed * magnitudeSmoothing;
        velocity[1] *= speed * magnitudeSmoothing;

        //change Sprite position based on velocity
        //also check if the player is at the world limit, if it is then it doesnt move
        if (this.getX() + velocity[0] < WorldModel.getInstance().getMapWidth()){
            if (this.getX() + velocity[0] > -WorldModel.getInstance().getMapWidth()){
                this.setX(this.getX() + velocity[0] );
            }
        }
        if (this.getY() + velocity[1] < WorldModel.getInstance().getMapHeight()){
            if (this.getY() + velocity[1] > -WorldModel.getInstance().getMapHeight()){
                this.setY(this.getY() + velocity[1]);
            }
        }

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
