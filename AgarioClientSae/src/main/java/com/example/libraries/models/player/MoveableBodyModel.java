package com.example.libraries.models.player;

import com.example.client.app.HelloApplication;
import com.example.libraries.models.worldElements.Entity;
import com.example.libraries.models.worldElements.WorldModel;
import org.w3c.dom.Node;

public class MoveableBodyModel extends Entity {

    protected double x, y; // Position
    protected double speed = 1.5;
    protected double weight;

    public MoveableBodyModel(double startX, double startY, double weight) {
        super(startX, startY, weight);
    }

    public void moveToward(double[] velocity) {
        double magnitude = Math.sqrt(velocity[0] * velocity[0] + velocity[1] * velocity[1]);

        if (magnitude > 0) {
            velocity[0] /= magnitude;
            velocity[1] /= magnitude;
        }

        velocity[0] *= speed;
        velocity[1] *= speed;

        x += velocity[0];
        y += velocity[1];

        // Empêcher de sortir des limites du monde
        WorldModel world = WorldModel.getInstance();
        x = Math.max(-world.getMapWidth(), Math.min(world.getMapWidth(), x));
        y = Math.max(-world.getMapHeight(), Math.min(world.getMapHeight(), y));
    }

    public double getX() { return x; }
    public double getY() { return y; }
    public double getWeight() { return weight; }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double[] getPosition() {
        return new double[]{x, y};
    }


}
