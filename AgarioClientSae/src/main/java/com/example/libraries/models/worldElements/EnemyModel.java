package com.example.libraries.models.worldElements;

import com.example.libraries.models.AI.EatPastilleAI;
import com.example.libraries.models.AI.IStrategyAI;
import com.example.libraries.models.AI.RandomMouvementAI;
import com.example.libraries.models.player.MoveableBodyModel;
import java.util.List;

public class EnemyModel extends MoveableBodyModel {

    private double closestEntityDistance;
    private Entity closestEntity;
    private IStrategyAI strategy;

    public EnemyModel(double startX, double startY, double weight, IStrategyAI strategy) {
        super(500, 500, weight);
        this.strategy = strategy;
        this.closestEntityDistance = Double.MAX_VALUE;
        this.closestEntity = null;
        System.out.println("EnemyModel instancié à : " + getX() + ", " + getY());
    }

    public void update(List<Entity> entities) {
        closestEntityDistance = Double.MAX_VALUE;
        closestEntity = null;

        for (Entity entity : entities) {
            if (!entity.equals(this)) {
                double distance = distanceTo(entity.getPosition());
                if (distance < closestEntityDistance) {
                    closestEntityDistance = distance;
                    closestEntity = entity;
                }
            }
        }

        if (closestEntity != null) {
            double[] targetPosition = strategy.move(this); // Position cible retournée par l'IA
            double dx = targetPosition[0] - getX();
            double dy = targetPosition[1] - getY();

            // Convertir en un vecteur direction normalisé
            double magnitude = Math.sqrt(dx * dx + dy * dy);
            double[] direction = (magnitude > 0) ? new double[]{dx / magnitude, dy / magnitude} : new double[]{0, 0};

            moveToward(direction); // Déplacement vers la cible
        }
    }

    public double distanceTo(double[] position) {
        double dx = position[0] - super.getX();
        double dy = position[1] - super.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }

    public Entity getClosestEntity() {
        return closestEntity;
    }
}
