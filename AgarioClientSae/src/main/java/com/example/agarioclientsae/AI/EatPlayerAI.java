package com.example.agarioclientsae.AI;

import com.example.agarioclientsae.worldElements.Enemy;

public class EatPlayerAI implements IStrategyAI {

    public double[] move(Enemy enemy){
        /*if(enemy.getClosestEntity() instanceof Food){
            for (Entity entity : World.getInstance().getEntities()) {
                if (!entity.equals(enemy) && !(entity instanceof Food)){
                    if (enemy.distanceTo(entity.getPosition()) < enemy.getClosestEntityDistance()) {
                        enemy.setClosestEntityDistance(enemy.distanceTo(entity.getPosition()));
                        enemy.setClosestEntity(entity);
                        return enemy.getClosestEntity().getPosition();
                    }
                }
            }
        }*/
        return enemy.getClosestEntity().getPosition();
    }

    @Override
    public void move() {

    }

}
