package com.example.agarioclientsae.AI;

import com.example.agarioclientsae.player.IPlayer;
import com.example.agarioclientsae.player.PlayableGroup;
import com.example.agarioclientsae.player.Player;
import com.example.agarioclientsae.worldElements.Enemy;
import com.example.agarioclientsae.worldElements.Entity;
import com.example.agarioclientsae.worldElements.Food;
import com.example.agarioclientsae.worldElements.World;
import javafx.scene.Node;

public class EatPlayerAI implements IStrategyAI {

    public double[] move(Enemy enemy){
        World world = World.getInstance();
        // Initialiser la distance minimale à une valeur élevée
        double minDistance = Double.MAX_VALUE;
        Enemy closestEnemy = null;
        IPlayer closestPlayer = null;

        // Parcourir tous les nœuds de la scène pour trouver les ennemis
        for (Node node : world.getRoot().getChildren()) {
            if (node instanceof Enemy) {
                Enemy potentialTarget = (Enemy) node;
                if (potentialTarget.equals(enemy)) {
                    continue;
                }
                double distance = enemy.distanceTo(potentialTarget.getPosition());

                // Vérifier si cet ennemi est plus proche que la précédente
                if (distance < minDistance) {
                    minDistance = distance;
                    closestEnemy = potentialTarget;
                }
            }else if(node instanceof IPlayer){
                IPlayer potentialTarget = (IPlayer) node;
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
