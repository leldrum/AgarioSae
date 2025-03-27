package com.example.libraries.models.player;

public interface IPlayerModel {
    double[] getPosition();
    void increaseSize(double foodValue);
    //double checkCollision();
    void update();
    double totalRadius();
    double getCenterX();
    double getCenterY();
    void moveToward(double[] velocity);
}
