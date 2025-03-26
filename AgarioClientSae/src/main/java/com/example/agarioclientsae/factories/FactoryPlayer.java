package com.example.agarioclientsae.factories;

import com.example.agarioclientsae.player.IPlayer;
import com.example.agarioclientsae.player.PlayableGroup;
import com.example.agarioclientsae.worldElements.World;
import com.example.agarioclientsae.player.Player;
import javafx.scene.Group;

public class FactoryPlayer implements Factory<Player> {

    @Override
    public PlayableGroup create(Group group, double weight) {
        System.out.println("avant"+group.getChildren());
        PlayableGroup p = new PlayableGroup(group, weight);
        System.out.println("apres" + group.getChildren());
        return p;
    }

}
