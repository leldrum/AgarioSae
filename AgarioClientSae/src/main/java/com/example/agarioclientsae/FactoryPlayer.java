package com.example.agarioclientsae;

import javafx.scene.Group;

public class FactoryPlayer implements Factory<Player>{

    @Override
    public Player create(Group group, double weight) {
        return new Player(group, weight);
    }

}
