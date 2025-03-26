package com.example.agarioclientsae.worldElements;

import com.example.agarioclientsae.AI.EatPastilleAI;
import com.example.agarioclientsae.AI.EatPlayerAI;
import com.example.agarioclientsae.AI.IStrategyAI;
import com.example.agarioclientsae.AI.RandomMouvementAI;
import com.example.agarioclientsae.player.MoveableBody;
import javafx.scene.Group;

public class Enemy extends MoveableBody {


    private double closestEntityDistance;
    private Entity closestEntity;

    private IStrategyAI strategy;

    private World world = World.getInstance();

    public Enemy(Group group, double initialSize, IStrategyAI strategie){
        super(group, initialSize);
        //new Enemy made and added to the group
        entity.setCenterX(Math.random() * (World.getMapLimitWidth() - 100) + 50);
        entity.setCenterY(Math.random() * (World.getMapLimitHeight() - 100) + 50);

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
        if(checkCollision()){
            closestEntityDistance = Double.MAX_VALUE;
        }

        if(World.getInstance().getEntities().size() > 15) {
            Entity exemple = World.getInstance().getEntities().get(15);

            //System.out.println("Exemple: " + exemple + exemple.getPosition()[0] + " " + exemple.getPosition()[1]);
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

    public IStrategyAI getStrategy() {
        return strategy;
    }

    public void setClosestEntity(Entity closestEntity) {
        this.closestEntity = closestEntity;
    }

    @Override
    public void onDeletion(){

    }
}
