package com.example.client.controllers;

import com.example.client.views.EntitiesView;
import com.example.libraries.models.player.MoveableBodyModel;
import com.example.libraries.models.player.PlayerModel;
import com.example.libraries.models.worldElements.Entity;
import com.example.libraries.models.worldElements.WorldModel;

import java.util.Objects;

public class MoveableBodyController {
    //faire une liste aussi
    private MoveableBodyModel model;

    public double checkCollision() {

        for (Entity entity : WorldModel.getInstance().getEntities()) {
            //System.out.println("Total entities: " + entity.getClass().getSimpleName());
            if(!(entity.equals(model))) {
                Entity collider = entity;



                double dx = collider.getX() - model.getX();
                double dy = collider.getY() - model.getY();
                double distance = Math.sqrt(dx * dx + dy * dy);
                double sumRadii = collider.getWeight() + model.getWeight(); // Somme des rayons

                if (distance <= sumRadii) { // Vérifie si les cercles se touchent ou se chevauchent
                    double foodValue = 0.5;
                    //System.out.println("NANANANANAN");

                    if (isSmaller(collider.getWeight(), model.getWeight())) {
                        WorldModel.getInstance().queueFree(collider);
                        foodValue += collider.getWeight() / 20;
                        //System.out.println("okkkkkk");
                        //System.out.println("Entity: " + collider.getClass().getSimpleName());
                        increaseSize(foodValue);
                        return foodValue;
                    }
                    WorldModel.getInstance().queueFree(model);
                    //changer le instance of player par LE PLAYER DU PC LOCAL SINON BUG
                    if(entity instanceof PlayerModel) {
                        ((PlayerModel) entity).GameOver = true;
                    }
                    //System.out.println("Dead");
                    return 1;
                }
            }
            }
        return 0;
    }

    // Détermine si une entité est plus petite qu'une autre
    private boolean isSmaller(double weightOne, double weightTwo) {
        return weightOne <= weightTwo + 2;
    }
    public void increaseSize(double foodValue) {
        double initialWeight = this.model.getWeight();
        this.model.setWeight(initialWeight + foodValue);
        Objects.requireNonNull(EntitiesView.getSprite(model)).setRadius(this.model.getWeight());
    }


    public MoveableBodyController(MoveableBodyModel model) {
        this.model = model;
    }

    public void update(double[] direction) {
        model.moveToward(direction);
    }

}
