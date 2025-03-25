package com.example.agarioclientsae;

import javafx.scene.Group;

public class FactoryFood implements Factory<Food>{

    private double weight;
    private Group group;

    public FactoryFood(Group group, double weight){
        this.weight = weight;
        this.group = group;
    }
    @Override
    public Food create() {
        return new Food(group, weight);
    }
}
