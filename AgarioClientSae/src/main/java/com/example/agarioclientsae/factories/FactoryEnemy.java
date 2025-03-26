package com.example.agarioclientsae.factories;

import com.example.agarioclientsae.AI.EatPastilleAI;
import com.example.agarioclientsae.AI.EatPlayerAI;
import com.example.agarioclientsae.AI.RandomMouvementAI;
import com.example.agarioclientsae.worldElements.Enemy;
import com.example.agarioclientsae.worldElements.World;
import com.example.agarioclientsae.AI.IStrategyAI;
import javafx.scene.Group;

import java.util.Random;

public class FactoryEnemy implements Factory<Enemy> {

    public static int id = 10000;
    @Override
    public Enemy create(Group group, double weight) {
        World world = World.getInstance();

        IStrategyAI[] strategies = {
                new EatPastilleAI(),
                new EatPlayerAI(),
                new RandomMouvementAI()
        };
        Random random = new Random();
        IStrategyAI selectedStrategy = strategies[random.nextInt(strategies.length)];

        Enemy enemy = new Enemy(group, weight, selectedStrategy);
        world.addEntity(enemy);
        return enemy;
    }
}
