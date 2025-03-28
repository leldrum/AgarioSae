package com.example.client.controllers;

import com.example.client.views.EntitiesView;
import com.example.client.views.PlayableGroupView;
import com.example.libraries.models.player.MoveableBodyModel;
import com.example.libraries.models.player.PlayerModel;
import com.example.libraries.models.worldElements.Entity;
import com.example.libraries.models.worldElements.WorldModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MoveableBodyController {
    //faire une liste aussi
    private ArrayList<MoveableBodyModel> models = new ArrayList<>();

    public double checkCollision(MoveableBodyModel entity) {

            for (Entity other : WorldModel.getInstance().getEntities()) {
                if(!(entity.equals(other))) {
                    Entity collider = entity;



                    double dx = collider.getX() - other.getX();
                    double dy = collider.getY() - other.getY();
                    double distance = Math.sqrt(dx * dx + dy * dy);
                    double sumRadii = collider.getWeight() + other.getWeight(); // Somme des rayons

                    if (distance <= sumRadii) { // Vérifie si les cercles se touchent ou se chevauchent
                        double foodValue = 0.5;
                        System.out.println("NANANANANAN");

                        if (isSmaller(other.getWeight(), collider.getWeight())) {
                            WorldModel.getInstance().queueFree(other);
                            foodValue += other.getWeight() / 20;
                            System.out.println("okkkkkk");
                            System.out.println("Entity: " + collider.getClass().getSimpleName());
                            increaseSize((MoveableBodyModel) collider,foodValue);
                            return foodValue;
                        }
                        else {
                            WorldModel.getInstance().queueFree(collider);
                            //changer le instance of player par LE PLAYER DU PC LOCAL SINON BUG
                            if(entity.equals(PlayableGroupView.player)) {
                                ((PlayerModel) entity).GameOver = true;
                            }
                            System.out.println("Dead");
                            return 1;
                        }

                    }

                }
            }




        return 0;
    }

    // Détermine si une entité est plus petite qu'une autre
    private boolean isSmaller(double weightOne, double weightTwo) {
        return weightOne <= weightTwo + 2;
    }
    public void increaseSize(MoveableBodyModel model,double foodValue) {
        double initialWeight = model.getWeight();
        model.setWeight(initialWeight + foodValue);
        Objects.requireNonNull(EntitiesView.getSprite(model)).setRadius(model.getWeight());
    }


    public MoveableBodyController(List<Entity> list) {
        for (Entity entity: list
             ) {
            if(entity instanceof MoveableBodyModel) {
                models.add((MoveableBodyModel) entity);
            }
        };
    }

    public void update(MoveableBodyModel model,double[] direction) {
        model.moveToward(direction);
    }

}
