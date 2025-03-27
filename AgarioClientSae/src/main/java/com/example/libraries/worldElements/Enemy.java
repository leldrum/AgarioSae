package com.example.libraries.worldElements;

import com.example.client.app.HelloApplication;
import com.example.libraries.AI.IStrategyAI;
import com.example.libraries.player.MoveableBody;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

public class Enemy extends MoveableBody {


    private double closestEntityDistance;
    private Entity closestEntity;

    private IStrategyAI strategy;

    private World world = World.getInstance();

    public Enemy(Group group, double initialSize, IStrategyAI strategie){
        super(group, initialSize);
        //new Enemy made and added to the group
        entity.setCenterX(Math.random() * (World.getInstance().getMapLimitWidth() - 100) + 50);
        entity.setCenterY(Math.random() * (World.getInstance().getMapLimitHeight() - 100) + 50);

        strategy = strategie;

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
            //System.out.println("Closest entity: " + closestEntity + closestEntity.getPosition()[0] + " " + closestEntity.getPosition()[1]);
            moveToward(strategy.move(this));
        }

        // Check if player is colliding with anything
        if(checkCollision() != 0){
            closestEntityDistance = Double.MAX_VALUE;
        }

        if(World.getInstance().getEntities().size() > 15) {
            Entity exemple = World.getInstance().getEntities().get(15);

            //System.out.println("Exemple: " + exemple + exemple.getPosition()[0] + " " + exemple.getPosition()[1]);
        }


    }

    public double checkCollision(){
        for(Node entity : HelloApplication.root.getChildren()) {
            if (entity instanceof Entity && entity != this.entity) {
                Entity collider = (Entity) entity;

                if (entity != this) {

                    Shape intersect = Shape.intersect(this.entity, collider.entity);

                    if (intersect.getBoundsInLocal().getWidth() != -1) {

                        double foodValue = 0.5;

                        if (isSmaller(collider.entity, this.entity)) {
                            World.queueFree(collider);
                            foodValue += collider.entity.getRadius() / 20;
                            increaseSize(foodValue);
                            return foodValue;
                        }
                        return 1;
                    }
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

    public double getClosestEntityDistance() {
        return closestEntityDistance;
    }

    public Entity getClosestEntity() {
        return closestEntity;
    }

    public void setClosestEntityDistance(double closestEntityDistance) {
        this.closestEntityDistance = closestEntityDistance;
    }

    public IStrategyAI getStrategy() {
        return strategy;
    }

    public void setClosestEntity(Entity closestEntity) {
        this.closestEntity = closestEntity;
    }

    @Override
    public void onDeletion(){

    }

    public void update() {
        checkCollision();

    }
}
