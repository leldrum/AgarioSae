package com.example.agarioclientsae;

public class FactoryFood implements Factory<Food>{

    private double weight;

    public FactoryFood(double weight){
        this.weight = weight;
    }
    @Override
    public Food create() {
        return new Food(weight);
    }
}
