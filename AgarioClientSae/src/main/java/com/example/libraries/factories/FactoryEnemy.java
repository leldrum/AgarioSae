package com.example.libraries.factories;

import com.example.libraries.AI.EatPastilleAI;
import com.example.libraries.AI.EatPlayerAI;
import com.example.libraries.AI.RandomMouvementAI;
import com.example.libraries.worldElements.Enemy;
import com.example.libraries.worldElements.World;
import com.example.libraries.AI.IStrategyAI;
import javafx.scene.Group;

import java.util.Random;

public class FactoryEnemy implements Factory<Enemy> {

    public static int id = 1000;
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

        Enemy enemy = new Enemy(group, weight, selectedStrategy, id);
        id++;
        world.addEntity(enemy);
        return enemy;
    }
}
