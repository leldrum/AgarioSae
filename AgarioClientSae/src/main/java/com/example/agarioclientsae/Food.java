package com.example.agarioclientsae;

public class Food extends Entity{


    public Food(double weight) {
        super(weight);
        entity.setCenterX(Math.random() * (AgarioApplication.getMapLimitWidth() * 2) - AgarioApplication.getMapLimitWidth());
        entity.setCenterY(Math.random() * (AgarioApplication.getMapLimitWidth() * 2) - AgarioApplication.getMapLimitWidth());
    }
}
