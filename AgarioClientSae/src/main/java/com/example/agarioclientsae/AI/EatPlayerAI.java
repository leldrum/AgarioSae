package com.example.agarioclientsae.AI;

import com.example.agarioclientsae.worldElements.Enemy;
import com.example.agarioclientsae.worldElements.Entity;
import com.example.agarioclientsae.worldElements.Food;
import com.example.agarioclientsae.worldElements.World;

public class EatPlayerAI implements IStrategyAI {

    @Override
    public double[] move(Enemy enemy) {
        World world = World.getInstance();
        double minDistance = Double.MAX_VALUE;
        Entity closestTarget = null;

        System.out.println("IA Enemy: Recherche d'une cible...");

        for (Entity entity : world.getEntities()) {
            if (!entity.equals(enemy) && !(entity instanceof Food)) {  // Ignorer Food et soi-même
                double distance = enemy.distanceTo(entity.getPosition());

                System.out.println("IA Enemy: Vérification de l'entité " + entity + " - Distance: " + distance);

                if (distance < minDistance) {
                    minDistance = distance;
                    closestTarget = entity;
                    System.out.println("IA Enemy: Nouvelle cible trouvée -> " + entity);
                }
            }
        }

        // Met à jour la cible
        if (closestTarget != null) {
            System.out.println("IA Enemy: Cible finale -> " + closestTarget);
            enemy.setClosestEntity(closestTarget);
            enemy.setClosestEntityDistance(minDistance);
            return closestTarget.getPosition();
        }

        // Aucun joueur trouvé, rester immobile
        System.out.println("IA Enemy: Aucune cible trouvée.");
        return enemy.getPosition();
    }

    @Override
    public void move() {
        // Pas utilisé
    }
}
