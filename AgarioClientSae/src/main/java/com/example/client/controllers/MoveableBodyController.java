package com.example.client.controllers;

import com.example.client.views.MoveableBodyView;
import com.example.libraries.models.player.MoveableBodyModel;
import com.example.libraries.models.worldElements.Entity;
import com.example.libraries.models.worldElements.WorldModel;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

public class MoveableBodyController {
    private MoveableBodyModel model;
    private MoveableBodyView view;

    public double checkCollision() {
        System.out.println("Total entities: " + WorldModel.getInstance().getEntities().size());

        for (Entity entity : WorldModel.getInstance().getEntities()) {
            if(!(entity.equals(model))) {
                Entity collider = entity;

                double dx = collider.getX() - model.getX();
                double dy = collider.getY() - model.getY();
                double distance = Math.sqrt(dx * dx + dy * dy);
                double sumRadii = collider.getWeight() + model.getWeight(); // Somme des rayons

                if (distance <= sumRadii && (sumRadii * distance)/100 >= 33) { // Vérifie si les cercles se touchent ou se chevauchent
                    double foodValue = 0.5;

                    if (isSmaller(collider.getWeight(), model.getWeight())) {
                        WorldModel.getInstance().queueFree(collider);
                        foodValue += collider.getWeight() / 20;
                        System.out.println("okkkkkk");
                        System.out.println("Entity: " + collider.getClass().getSimpleName());
                        increaseSize(foodValue);
                        return foodValue;
                    }
                    System.out.println("Dead");
                    return 1;
                }
            }
            }
        return 0;
    }


    public MoveableBodyView getView() {
        return view;
    }

    // Détermine si une entité est plus petite qu'une autre
    private boolean isSmaller(double weightOne, double weightTwo) {
        return weightOne <= weightTwo + 2;
    }
    // Augmente la taille du joueur
    public void increaseSize(double foodValue) {
        double initialWeight = this.model.getWeight();
        this.model.setWeight(initialWeight + foodValue);
        this.view.getSprite().setRadius(this.model.getWeight()); // Mise à jour de la taille du joueur
    }


    public MoveableBodyController(MoveableBodyModel model, MoveableBodyView view) {
        this.model = model;
        this.view = view;
    }

    public void update(double[] velocity) {
        model.moveToward(velocity);
        view.updateView();
    }
}
