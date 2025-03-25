package com.example.agarioclientsae;

public class EatPlayerAI implements IStrategyAI{

    public double[] move(Enemy enemy){
        return enemy.getClosestEntity().getPosition();

    }

    @Override
    public void move() {

    }

}
