package com.example.agarioclientsae;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class Enemy extends MoveableBody{


    private double closestEntityDistance;
    private Entity closestEntity;

    private IStrategyAI strategy = new EatPlayerAI();

    private World world = World.getInstance();

    Enemy(Group group, double initialSize){
        super(group, initialSize);
        //new Enemy made and added to the group
        entity.setCenterX(Math.random() * (World.getMapLimitWidth() - 100) + 50);
        entity.setCenterY(Math.random() * (World.getMapLimitHeight() - 100) + 50);

        closestEntityDistance = Double.MAX_VALUE;
        closestEntity = null;
    }

    @Override
    public void Update(){

        world.getEntities().forEach(entity -> {
            if (!entity.equals(this)){
                double distance = distanceTo(entity.getPosition());
                if (distance < closestEntityDistance) {
                    closestEntityDistance = distance;
                    closestEntity = entity;
                }
            }
        });



        if (closestEntity != null) {
            moveToward(strategy.move(this));
        }

        // Check if player is colliding with anything
        checkCollision();

        if(World.getInstance().getEntities().size() > 15) {
            Entity exemple = World.getInstance().getEntities().get(15);

            System.out.println("Exemple: " + exemple + exemple.getPosition()[0] + " " + exemple.getPosition()[1]);
        }


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
