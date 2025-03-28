package com.example.libraries.models.AI;

import com.example.libraries.models.worldElements.EnemyModel;
import com.example.libraries.models.worldElements.Entity;
import com.example.libraries.models.worldElements.WorldModel;
import com.example.libraries.models.player.IPlayerModel;

public class EatPlayerAI implements IStrategyAI {

    @Override
    public double[] move(EnemyModel enemy) {
        WorldModel world = WorldModel.getInstance();
        double minDistance = Double.MAX_VALUE;
        Entity closestEnemy = null;

        // Recherche de l'ennemi ou du joueur le plus proche
        for (Entity entity : world.getEntities()) {
            if (entity.equals(enemy)) continue; // Ne pas se cibler soi-même

            if (entity instanceof EnemyModel || entity instanceof IPlayerModel) {
                double distance = enemy.distanceTo(entity.getPosition());

                if (distance < minDistance) {
                    minDistance = distance;
                    closestEnemy = entity;
                }
            }
        }

        // Se déplacer vers l'ennemi ou le joueur trouvé
        if (closestEnemy != null) {
            return closestEnemy.getPosition();
        }

        // Si aucun ennemi n'est trouvé, rester immobile
        return enemy.getPosition();
    }

}
