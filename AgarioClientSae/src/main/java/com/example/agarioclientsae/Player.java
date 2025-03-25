package com.example.agarioclientsae;

import javafx.scene.ParallelCamera;

public class Player extends Entity{

    public ParallelCamera camera = new ParallelCamera();


    public Player(double weight) {
        super(weight);
        entity.setCenterX(0);
        entity.setCenterY(0);
        entity.setViewOrder(-entity.getRadius());
    }
}
