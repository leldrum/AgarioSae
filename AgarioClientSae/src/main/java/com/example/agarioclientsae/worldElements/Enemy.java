package com.example.agarioclientsae.worldElements;

import com.example.agarioclientsae.AI.EatPastilleAI;
import com.example.agarioclientsae.AI.EatPlayerAI;
import com.example.agarioclientsae.AI.IStrategyAI;
import com.example.agarioclientsae.AI.RandomMouvementAI;
import com.example.agarioclientsae.worldElements.Entity;
import com.example.agarioclientsae.player.MoveableBody;
import javafx.scene.Group;
import javafx.scene.layout.Pane;

public class Enemy extends MoveableBody {


    private double closestEntityDistance;
    private Entity closestEntity;

    private IStrategyAI strategy = new EatPastilleAI();

    private World world = World.getInstance();

    public Enemy(Pane gamePane, double initialSize){
        super(gamePane, initialSize);
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
