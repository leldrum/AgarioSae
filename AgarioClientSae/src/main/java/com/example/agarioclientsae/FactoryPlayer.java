package com.example.agarioclientsae;

public class FactoryPlayer implements Factory<Player>{

    private double weight;

    public FactoryPlayer(double weight){
        this.weight = weight;
    }
    @Override
    public Player create() {
        return new Player(weight);
    }
}
