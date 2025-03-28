package com.example.libraries.models.AI;

import com.example.libraries.models.worldElements.EnemyModel;
import com.example.libraries.models.worldElements.Entity;
import com.example.libraries.models.worldElements.Food;
import com.example.libraries.models.worldElements.WorldModel;

public class EatPastilleAI implements IStrategyAI {

    @Override
    public double[] move(EnemyModel enemy) {
        WorldModel world = WorldModel.getInstance();
        double minDistance = Double.MAX_VALUE;
        Food closestFood = null;

        System.out.println("L'ennemi " + enemy.getClass().getSimpleName() + " cherche une pastille...");
        // Recherche de la pastille la plus proche
        for (Entity entity : world.getEntities()) {
            if (entity instanceof Food) {
                Food food = (Food) entity;
                double distance = enemy.distanceTo(food.getPosition());

                System.out.println("Pastille détectée à (" + food.getPosition()[0] + ", " + food.getPosition()[1] + ") - Distance : " + distance);

                if (distance < minDistance) {
                    minDistance = distance;
                    closestFood = food;
                }
            }
        }

        // Se déplacer vers la pastille trouvée
        if (closestFood != null) {
            System.out.println("Cible sélectionnée : pastille à (" + closestFood.getPosition()[0] + ", " + closestFood.getPosition()[1] + ")");
            return closestFood.getPosition();
        }

        // Si aucune nourriture n'est trouvée, rester immobile
        return enemy.getPosition();
    }

}
