package com.example.agarioclientsae.factories;

import com.example.agarioclientsae.worldElements.Food;
import com.example.agarioclientsae.worldElements.World;
import javafx.scene.Group;
import javafx.scene.layout.Pane;

public class FactoryFood implements Factory<Food> {


    @Override
    public Food create(Pane gamePane, double weight) {
        World world = World.getInstance();
        Food food = new Food(gamePane, weight);
        gamePane.getChildren().add(food);
        world.addEntity(food);


        return food;
    }
}
