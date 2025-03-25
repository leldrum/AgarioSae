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
        entity.setCenterX((Math.random() * World.getMapLimitWidth() * 2) - World.getMapLimitWidth());
        entity.setCenterY((Math.random() * World.getMapLimitHeight() * 2) - World.getMapLimitHeight());

        closestEntityDistance = (distanceTo(world.getPlayer().getPosition()));
        closestEntity = (world.getPlayer());

        //puts the Enemy infront of all the food
        //puts bigger entities in front of smaller entities
    }

    @Override
    public void Update(){
        //move player towards the mouse position

        //System.out.println(world.getEntities());

        world.getEntities().forEach(entity -> {
            switch (entity) {
                case MoveableBody each:
                    if (!each.equals(this)){
                        if (distanceTo(each.getPosition()) < closestEntityDistance) {
                            closestEntityDistance = (distanceTo(each.getPosition()));
                            closestEntity = (each);

                            System.out.println(closestEntity);

                        }
                    }
                    break;
                default:
                    break;
            }
        });

        moveToward(strategy.move(this));

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
