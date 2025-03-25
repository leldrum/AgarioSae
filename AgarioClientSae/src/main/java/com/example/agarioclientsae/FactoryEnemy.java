package com.example.agarioclientsae;

import javafx.scene.Group;

public class FactoryEnemy implements Factory<Enemy>{
    @Override
    public Enemy create(Group group, double weight) {
        return new Enemy(group, weight);
    }
}
