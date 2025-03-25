package com.example.agarioclientsae;

public interface IStrategyAI {
    double[] move(World world, Enemy enemy);

    void move();
}
