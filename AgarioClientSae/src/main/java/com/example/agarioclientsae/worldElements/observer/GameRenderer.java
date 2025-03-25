package com.example.agarioclientsae.worldElements.observer;

import com.example.agarioclientsae.worldElements.observer.WorldObserver;
import com.example.agarioclientsae.player.Player;
import com.example.agarioclientsae.worldElements.Entity;

import java.util.HashMap;
import java.util.List;

public class GameRenderer implements WorldObserver {
    @Override
    public void onEntitiesAbsorbed(HashMap<Player, List<Entity>> absorbedEntities) {
        for (Player player : absorbedEntities.keySet()) {
            System.out.println("Le joueur " + player.getId() + " a absorbé :");
            for (Entity entity : absorbedEntities.get(player)) {
                System.out.println("  - " + entity);
            }
        }
    }
}
