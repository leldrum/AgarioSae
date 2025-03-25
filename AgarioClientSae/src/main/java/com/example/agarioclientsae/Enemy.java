package com.example.agarioclientsae;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class Enemy extends MoveableBody{


    private double closestEntityDistance;
    private Entity closestEntity;

    private IStrategyAI strategy = new EatPlayerAI();

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

        moveToward(strategy.move(world,this));

        

        moveToward(closestEntity.getPosition());

        //check if player is colliding with anything
        checkCollision();
    }

    public double getClosestEntityDistance() {
        return closestEntityDistance;
    }

    public Entity getClosestEntity() {
        return closestEntity;
    }

    public void setClosestEntityDistance(double closestEntityDistance) {
        this.closestEntityDistance = closestEntityDistance;
    }

    public void setClosestEntity(Entity closestEntity) {
        this.closestEntity = closestEntity;
    }

    @Override
    public void onDeletion(){

    }
}
