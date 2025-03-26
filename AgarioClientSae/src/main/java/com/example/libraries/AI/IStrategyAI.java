package com.example.libraries.AI;

import com.example.libraries.worldElements.Enemy;

public interface IStrategyAI {
    double[] move(Enemy enemy);

    void move();
}
