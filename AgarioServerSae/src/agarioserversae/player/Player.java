
package agarioserversae.player;

import agarioserversae.app.HelloApplication;
import agarioserversae.worldElements.World;
import javafx.animation.ScaleTransition;
import javafx.scene.Group;
import javafx.util.Duration;

public class Player extends MoveableBody{

    public CameraPlayer camera = new CameraPlayer(); // creates a camera for the agarioserversae.player

    public double[] cameraScale = {camera.getScaleX(), camera.getScaleY()};

    public Player(Group group, double initialSize){
        super(group, initialSize);
        //new agarioserversae.player made and added to the group
        entity.setCenterX(0);
        entity.setCenterY(0);

        //puts the agarioserversae.player infront of all the food
        entity.setViewOrder(-entity.getRadius());
    }

    public void increaseSize(double foodValue){
        super.increaseSize(foodValue);
        //zoom out the camera when the agarioserversae.player gets too big

        ScaleTransition cameraZoom = new ScaleTransition(Duration.millis(200), camera);

        if (entity.getRadius() > 70){
            cameraScale[0] += foodValue / 200;
            cameraScale[1] += foodValue / 200;
        }


        cameraZoom.setToX(cameraScale[0]);
        cameraZoom.setToY(cameraScale[1]);
        cameraZoom.play();

    }

    public void moveToward(double[] velocity) {
        super.moveToward(velocity);
        velocity = normalizeDouble(velocity);
        //set the position of the camera to the same position as the agarioserversae.player
        //minus by half of the screen resolution, keep the agarioserversae.player in the middle of the screen
        camera.setLayoutX((entity.getCenterX() + velocity[0]) - HelloApplication.getScreenWidth() / 2 * camera.getScaleX());
        camera.setLayoutY((entity.getCenterY() + velocity[1])  - HelloApplication.getScreenHeight() / 2 * camera.getScaleY());
    }


    public void gameOver(){
        World.queueFree(entity);
    }

    @Override
    public void Update(){
        //move agarioserversae.player towards the mouse position
        moveToward(HelloApplication.getMousePosition());

        //check if agarioserversae.player is colliding with anything
        checkCollision();
    }

}
