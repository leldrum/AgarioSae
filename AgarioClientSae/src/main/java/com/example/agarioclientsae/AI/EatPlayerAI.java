package com.example.agarioclientsae.AI;

import com.example.agarioclientsae.worldElements.Enemy;
import com.example.agarioclientsae.worldElements.Entity;
import com.example.agarioclientsae.worldElements.Food;
import com.example.agarioclientsae.worldElements.World;

public class EatPlayerAI implements IStrategyAI {

    public double[] move(Enemy enemy){
        if(enemy.getClosestEntity() instanceof Food){
            for (Entity entity : World.getInstance().getEntities()) {
                if (!entity.equals(enemy) && !(entity instanceof Food)){
                    enemy.setClosestEntity(entity);
                    enemy.setClosestEntityDistance(enemy.distanceTo(entity.getPosition()));
                    if (enemy.distanceTo(entity.getPosition()) < enemy.getClosestEntityDistance()) {
                        enemy.setClosestEntityDistance(enemy.distanceTo(entity.getPosition()));
                        enemy.setClosestEntity(entity);
                        return enemy.getClosestEntity().getPosition();
                    }
                }
            }
        }

        return enemy.getClosestEntity().getPosition();


    }

    @Override
    public void move() {

    }

}
