package com.example.libraries.player;

import javafx.scene.Camera;

public interface IPlayer {

    public double[] getPosition(); // [x, y
    public void increaseSize(double foodValue);
    public double checkCollision();

    public void Update();

    public double totalRadius();

    public double getCenterX();

    public double getCenterY();

    public void moveToward(double[] velocity);
    Camera getCamera();
}
