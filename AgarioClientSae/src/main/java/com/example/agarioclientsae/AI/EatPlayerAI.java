package com.example.agarioclientsae.AI;

import com.example.agarioclientsae.worldElements.Enemy;
import com.example.agarioclientsae.worldElements.Entity;
import com.example.agarioclientsae.worldElements.Food;
import com.example.agarioclientsae.worldElements.World;

public class EatPlayerAI implements IStrategyAI {

    public double[] move(Enemy enemy){
        System.out.println(World.getInstance().getEntities());
        if(enemy.getClosestEntity() instanceof Food){
            System.out.println(3);

            for (Entity entity : World.getInstance().getEntities()) {
                if (!entity.equals(enemy) && !(entity instanceof Food)){
                    System.out.println(4);
                    enemy.setClosestEntity(entity);
                    enemy.setClosestEntityDistance(enemy.distanceTo(entity.getPosition()));
                    if (enemy.distanceTo(entity.getPosition()) < enemy.getClosestEntityDistance()) {
                        enemy.setClosestEntityDistance(enemy.distanceTo(entity.getPosition()));
                        enemy.setClosestEntity(entity);
                        System.out.println(2);
                        return enemy.getClosestEntity().getPosition();
                    }
                }
            }
        }

        System.out.println(1);
        return enemy.getClosestEntity().getPosition();


    }

    @Override
    public void move() {

    }

}
