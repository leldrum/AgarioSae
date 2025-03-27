package com.example.libraries.models.factories;

import com.example.libraries.models.player.PlayerModel;
import javafx.scene.Group;

public class FactoryPlayer implements Factory<PlayerModel> {

    static int groupP = 0;
    @Override
    public PlayerModel create(double x, double y, double weight) {
        PlayerModel p = new PlayerModel(x,y, weight);
        groupP++;
        return p;
    }

}
