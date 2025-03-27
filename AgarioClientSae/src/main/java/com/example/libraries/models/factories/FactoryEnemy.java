package com.example.libraries.models.factories;

import com.example.libraries.models.AI.EatPastilleAI;
import com.example.libraries.models.worldElements.EnemyModel;
import com.example.libraries.models.worldElements.WorldModel;
import com.example.libraries.models.AI.EatPlayerAI;
import com.example.libraries.models.AI.RandomMouvementAI;
import com.example.libraries.models.AI.IStrategyAI;
import javafx.scene.Group;

import java.util.Random;

public class FactoryEnemy implements Factory<EnemyModel> {

    public static int id = 1000;

    public EnemyModel create(double x, double y, double weight) {
        WorldModel world = WorldModel.getInstance();

        IStrategyAI[] strategies = {
                new EatPastilleAI(),
                new EatPlayerAI(),
                new RandomMouvementAI()
        };
        Random random = new Random();
        IStrategyAI selectedStrategy = strategies[random.nextInt(strategies.length)];

        EnemyModel enemy = new EnemyModel(x, y, weight, selectedStrategy);
        id++;
        world.addEntity(enemy);
        return enemy;
    }
}
