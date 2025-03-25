package com.example.agarioclientsae;

import javafx.scene.Group;

public class FactoryPlayer implements Factory<Player>{

    private double weight;
    private Group group;

    public FactoryPlayer(Group group,double weight){
        this.weight = weight;
        this.group = group;
    }
    @Override
    public Player create() {
        return new Player(group, weight);
    }
}
