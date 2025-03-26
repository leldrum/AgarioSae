package agarioserversae.factories;

import agarioserversae.AI.EatPastilleAI;
import agarioserversae.AI.EatPlayerAI;
import agarioserversae.AI.RandomMouvementAI;
import agarioserversae.worldElements.Enemy;
import agarioserversae.worldElements.World;
import agarioserversae.AI.IStrategyAI;
import javafx.scene.Group;

import java.util.Random;

public class FactoryEnemy implements Factory<Enemy> {
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
