package com.example.agarioclientsae.factories;

import com.example.agarioclientsae.worldElements.World;
import com.example.agarioclientsae.player.Player;
import javafx.scene.Group;
import javafx.scene.layout.Pane;

public class FactoryPlayer implements Factory<Player> {

    @Override
    public Player create(Pane gamePane, double weight) {
        World world = World.getInstance();
        Player p = new Player(gamePane, weight);
        world.addEntity(p);
        return p;
    }

}
