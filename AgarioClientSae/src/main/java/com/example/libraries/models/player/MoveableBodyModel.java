package com.example.libraries.models.player;

import com.example.client.app.HelloApplication;
import com.example.libraries.models.worldElements.Entity;
import com.example.libraries.models.worldElements.WorldModel;
import org.w3c.dom.Node;

public class MoveableBodyModel extends Entity {

    protected double speed = 1.5;

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

        setPosition(getX() + velocity[0], getY() + velocity[1]);


        // Empêcher de sortir des limites du monde
        WorldModel world = WorldModel.getInstance();
        setPosition(
                Math.max(0, Math.min(world.getMapWidth(), getX())),
                Math.max(0, Math.min(world.getMapHeight(), getY()))
        );
    }


}
