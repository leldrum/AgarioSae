package com.example.libraries.models.worldElements;

import com.example.libraries.models.AI.IStrategyAI;
import com.example.libraries.models.player.MoveableBodyModel;
import java.util.List;

public class EnemyModel extends MoveableBodyModel {

    private double closestEntityDistance;
    private Entity closestEntity;
    private IStrategyAI strategy;

    public EnemyModel(double startX, double startY, double weight, IStrategyAI strategy) {
        super(startX, startY, weight);
        this.strategy = strategy;
        this.closestEntityDistance = Double.MAX_VALUE;
        this.closestEntity = null;
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
            moveToward(strategy.move(this));
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
