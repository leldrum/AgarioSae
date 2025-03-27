package com.example.libraries.models.AI;

import com.example.libraries.models.player.IPlayerModel;
import com.example.libraries.models.worldElements.EnemyModel;
import com.example.libraries.models.worldElements.Entity;
import com.example.libraries.models.worldElements.WorldModel;
import javafx.scene.Node;

public class EatPlayerAI implements IStrategyAI {

    public double[] move(EnemyModel enemy){
        WorldModel world = WorldModel.getInstance();
        // Initialiser la distance minimale à une valeur élevée
        double minDistance = Double.MAX_VALUE;
        EnemyModel closestEnemy = null;
        IPlayerModel closestPlayer = null;

        // Parcourir tous les nœuds de la scène pour trouver les ennemis
        for (Entity entity : WorldModel.getInstance().getEntities()) {
            if (entity instanceof EnemyModel) {
                EnemyModel potentialTarget = (EnemyModel) entity;
                if (potentialTarget.equals(enemy)) {
                    continue;
                }
                double distance = enemy.distanceTo(potentialTarget.getPosition());

                // Vérifier si cet ennemi est plus proche que la précédente
                if (distance < minDistance) {
                    minDistance = distance;
                    closestEnemy = potentialTarget;
                }
            }else if(entity instanceof IPlayerModel){
                IPlayerModel potentialTarget = (IPlayerModel) entity;
                if (potentialTarget.equals(enemy)) {
                    continue;
                }
                double distance = enemy.distanceTo(potentialTarget.getPosition());

                // Vérifier si cet ennemi est plus proche que la précédente
                if (distance < minDistance) {
                    minDistance = distance;
                    closestPlayer = potentialTarget;
                }
            }
        }

        // Si un ennemi a été trouvée, calculer le vecteur de déplacement vers elle
        if (closestEnemy != null) {
            double[] position = closestEnemy.getPosition();
            return new double[]{position[0], position[1]};
        }

        if (closestPlayer != null) {
            double[] position = closestPlayer.getPosition();
            return new double[]{position[0], position[1]};
        }

        // Si aucun ennnemi n'est trouvée, rester immobile
        return new double[]{0, 0};
    }

    @Override
    public void move() {
        // Pas utilisé
    }
}
