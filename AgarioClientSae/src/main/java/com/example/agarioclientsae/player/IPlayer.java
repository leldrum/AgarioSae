package com.example.agarioclientsae.player;

import javafx.scene.Camera;

public interface IPlayer {
    public void increaseSize(double foodValue);
    public Boolean checkCollision();

    public void Update();

    public double totalRadius();

    public double getCenterX();

    public double getCenterY();

    public void moveToward(double[] velocity);
    Camera getCamera();
}
