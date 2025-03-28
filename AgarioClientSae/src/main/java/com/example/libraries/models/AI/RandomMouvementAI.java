package com.example.libraries.models.AI;

import com.example.libraries.models.worldElements.EnemyModel;
import com.example.libraries.models.worldElements.WorldModel;

import java.util.Random;


public class RandomMouvementAI implements IStrategyAI{
    private Random random = new Random();
    private double targetX;
    private double targetY;
    private double interval = 10; // Nombre de mises à jour avant de changer de cible
    private double updateCounter = 0;

    public RandomMouvementAI() {
        setNewTarget();
    }

    @Override
    public double[] move(EnemyModel enemy) {
        updateCounter++;

        Random random = new Random();
        interval = random.nextDouble() * 100; // Génère un nouvel intervalle aléatoire

        // Vérifie si l'ennemi est proche de la cible ou si l'intervalle est écoulé
        if (updateCounter >= interval || isNearTarget(enemy)) {
            setNewTarget();
            updateCounter = 0;
        }

        return new double[]{targetX, targetY};
    }

    private void setNewTarget() {
        // Définit une nouvelle position cible aléatoire dans les limites de la carte
        targetX = (random.nextDouble() * WorldModel.getInstance().getMapWidth() * 2) - WorldModel.getInstance().getMapWidth();
        targetY = (random.nextDouble() * WorldModel.getInstance().getMapHeight() * 2) - WorldModel.getInstance().getMapHeight();
    }

    private boolean isNearTarget(EnemyModel enemy) {
        double dx = targetX - enemy.getPosition()[0];
        double dy = targetY - enemy.getPosition()[1];
        double distance = Math.sqrt(dx * dx + dy * dy);
        return distance < 10;
    }

}
