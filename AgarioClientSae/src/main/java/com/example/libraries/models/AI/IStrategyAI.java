package com.example.libraries.models.AI;

import com.example.libraries.models.worldElements.EnemyModel;

public interface IStrategyAI {
    double[] move(EnemyModel enemy);
}
