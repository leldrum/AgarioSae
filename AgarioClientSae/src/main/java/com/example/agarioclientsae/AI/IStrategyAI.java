package com.example.agarioclientsae.AI;

import com.example.agarioclientsae.worldElements.Enemy;

public interface IStrategyAI {
    double[] move(Enemy enemy);

    void move();
}
