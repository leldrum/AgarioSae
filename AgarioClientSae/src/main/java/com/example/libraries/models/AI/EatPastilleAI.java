package com.example.libraries.models.AI;

import com.example.libraries.models.worldElements.EnemyModel;
import com.example.libraries.models.worldElements.Entity;
import com.example.libraries.models.worldElements.Food;
import com.example.libraries.models.worldElements.WorldModel;
import javafx.scene.Node;

public class EatPastilleAI implements IStrategyAI {

    @Override
    public double[] move(EnemyModel enemy) {

        WorldModel world = WorldModel.getInstance();
        // Initialiser la distance minimale à une valeur élevée
        double minDistance = Double.MAX_VALUE;
        Food closestFood = null;

        // Parcourir tous les nœuds de la scène pour trouver les pastilles de nourriture
        for (Entity entity : WorldModel.getInstance().getEntities()) {
            if (entity instanceof Food) {
                Food food = (Food) entity;
                double distance = enemy.distanceTo(food.getPosition());

                // Vérifier si cette pastille est plus proche que la précédente
                if (distance < minDistance) {
                    minDistance = distance;
                    closestFood = food;
                }
            }
        }

        // Si une pastille de nourriture a été trouvée, calculer le vecteur de déplacement vers elle
        if (closestFood != null) {
            double[] foodPosition = closestFood.getPosition();
            return new double[]{foodPosition[0], foodPosition[1]};
        }

        // Si aucune nourriture n'est trouvée, rester immobile
        return new double[]{enemy.getPosition()[0], enemy.getPosition()[1]};
    }


    public void move(){

    }
}