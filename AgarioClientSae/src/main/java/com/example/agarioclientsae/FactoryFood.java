package com.example.agarioclientsae;

import javafx.scene.Group;

public class FactoryFood implements Factory<Food>{


    @Override
    public Food create(Group group, double weight) {
        return new Food(group, weight);
    }
}
