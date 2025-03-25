package com.example.agarioclientsae;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class Enemy extends MoveableBody{


    private double closestEntityDistance;
    private Entity closestEntity;

    Enemy(Group group, double initialSize){
        super(group, initialSize);
        //new Enemy made and added to the group
        entity.setCenterX((Math.random() * World.getMapLimitWidth() * 2) - World.getMapLimitWidth());
        entity.setCenterY((Math.random() * World.getMapLimitHeight() * 2) - World.getMapLimitHeight());

        //puts the Enemy infront of all the food
        //puts bigger entities in front of smaller entities
    }

    @Override
    public void Update(World world){
        //move player towards the mouse position
        
        closestEntityDistance = distanceTo(world.getPlayer().getPosition());
        closestEntity = world.getPlayer();

        world.root.getChildren().forEach(entity ->{
            switch (entity) {
                case MoveableBody each: 
                if (each != this){
                    if (distanceTo(each.getPosition()) < closestEntityDistance) {
                        closestEntityDistance = distanceTo(each.getPosition());
                        closestEntity = each;
                    
                    }
                } 

                default : break;
            }
           
        });

        

        moveToward(closestEntity.getPosition());

        //check if player is colliding with anything
        checkCollision();
    }

    @Override
    public void onDeletion(){

    }
}
