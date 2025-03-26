package com.example.agarioclientsae.worldElements.observer;

import com.example.agarioclientsae.player.Player;
import com.example.agarioclientsae.worldElements.Entity;

import java.util.HashMap;
import java.util.List;

public interface WorldObserver {
    void onEntitiesAbsorbed(HashMap<Player, List<Entity>> absorbedEntities);
}
