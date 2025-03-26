package agarioserversae.AI;

import agarioserversae.worldElements.Enemy;

public interface IStrategyAI {
    double[] move(Enemy enemy);

    void move();
}
