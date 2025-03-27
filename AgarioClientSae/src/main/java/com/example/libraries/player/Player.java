
package com.example.libraries.player;

import com.example.client.app.HelloApplication;
import com.example.libraries.worldElements.World;
import javafx.scene.Camera;
import javafx.scene.Group;

import java.util.Random;

public class Player implements IPlayer{

    public MoveableBody sprite;

    @Override
    public double[] getPosition() {
        return sprite.getPosition();
    }

    public Player(Group group, double initialSize, int groupP){
        sprite = new MoveableBody(group, initialSize, groupP);
        Random rand = new Random();
        sprite.entity.setCenterX(0);
        sprite.entity.setCenterY(0);
        sprite.setViewOrder(-sprite.entity.getRadius());
    }

    public void increaseSize(double foodValue){
        sprite.setWeight(sprite.getWeight() + foodValue);
        sprite.setViewOrder(-sprite.entity.getRadius());
        //zoom out the camera when the player gets too big
    }

    public double getCenterX(){
        return sprite.entity.getCenterX();
    }

    public double getCenterY(){
        return sprite.entity.getCenterY();
    }

    public double totalRadius(){
        double total = 0;
        total += sprite.entity.getRadius();
        return total;
    }

    public double checkCollision(){
        double result = sprite.checkCollision();
        if(result != 0){
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

        World.getInstance().updateMinimap();

    }

    public Player divide() {
        if (sprite.getWeight() > 50) {
            double newRadius = this.sprite.entity.getRadius();
            Player newPlayer = new Player(HelloApplication.root, sprite.getWeight() / 2, this.sprite.groupP);

            double newX = this.sprite.entity.getCenterX();
            double newY = this.sprite.entity.getCenterY();

            // Adjust position to form a square
            int numCells = World.getInstance().getPlayer().parts.size();
            int sqrt = (int) Math.sqrt(numCells);
            int row = numCells / sqrt;
            int col = numCells % sqrt;

            newX += (col * (newRadius * 2 + 1));
            newY += (row * (newRadius * 2 + 1));

            // Adjust position to avoid overlap
            while (isOverlapping(newX, newY, newRadius)) {
                if(World.getInstance().getPlayer().parts.size() / 2 % 2 == 0){
                    newX += newRadius * 2 + 1;
                }
                else{
                    newY += newRadius * 2 + 1;
                }
            }

            newPlayer.sprite.entity.setCenterX(newX);
            newPlayer.sprite.entity.setCenterY(newY);
            return newPlayer;
        }
        return null;
    }

    private boolean isOverlapping(double x, double y, double radius) {
        // Check for overlap with other cells
        for (Player player : World.getInstance().getPlayer().parts) {
            if (player != this) {
                double distance = Math.sqrt(Math.pow(player.getCenterX() - x, 2) + Math.pow(player.getCenterY() - y, 2));
                if (distance < player.totalRadius() + radius) {
                    return true;
                }
            }
        }
        return false;
    }

    // Force la mise à jour de la minimap


}
