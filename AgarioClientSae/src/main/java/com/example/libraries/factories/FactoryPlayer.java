package com.example.libraries.factories;

import com.example.libraries.player.PlayableGroup;
import com.example.libraries.player.Player;
import javafx.scene.Group;

public class FactoryPlayer implements Factory<Player> {

    static int groupP = 0;
    @Override
    public PlayableGroup create(Group group, double weight) {
        PlayableGroup p = new PlayableGroup(group, weight, groupP);
        groupP++;
        return p;
    }

}
