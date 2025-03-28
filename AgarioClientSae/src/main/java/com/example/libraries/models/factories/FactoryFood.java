package com.example.libraries.models.factories;

import com.example.libraries.models.worldElements.Food;
import com.example.libraries.models.worldElements.WorldModel;

public class FactoryFood implements Factory<Food> {


    @Override
    public Food create(double x, double y, double weight) {
        WorldModel world = WorldModel.getInstance();
        Food food = new Food(x, y, weight);
        world.addEntity(food);

        return food;
    }
}
