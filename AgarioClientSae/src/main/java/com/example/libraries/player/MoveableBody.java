package com.example.libraries.player;

import com.example.libraries.worldElements.Entity;
import com.example.client.app.HelloApplication;
import com.example.libraries.options.SpecialFood;
import com.example.libraries.worldElements.World;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;


public class MoveableBody extends Entity {

    public double Speed = 1.5; // self explanatory, the player's speed
    protected Group group;

    private double defaultSpeed = Speed;

    protected MoveableBody(Group group, double initialSize){
        super(group, initialSize);
        this.group = group;
    }

    public void setSpeed(double speed) {
        Speed = speed;
    }

    public double getSpeed() {
        return Speed;
    }

    public double checkCollision(){
        for(Node entity : HelloApplication.root.getChildren()) {
            if (entity instanceof Entity && entity != this.entity) {
                Entity collider = (Entity) entity;

                if (entity != this) {

                    Shape intersect = Shape.intersect(this.entity, collider.entity);

                    if (intersect.getBoundsInLocal().getWidth() != -1) {

                        double foodValue = 0.5;

                        if (collider instanceof SpecialFood) {
                            ((SpecialFood) collider).applyEffect(this);
                        }

                        if (isSmaller(collider.entity, this.entity)) {
                            World.queueFree(collider);
                            foodValue += collider.entity.getRadius() / 20;
                            increaseSize(foodValue);
                            return foodValue;
                        }
                        System.out.println("Dead");
                        return 1;
                    }
                }
            }
        }
        return 0;
    }

    public void applySpeedBoost(double factor, int duration) {
       Speed = defaultSpeed;
        Speed *= factor;
        System.out.println("Speed Boost activé ! Nouvelle vitesse : " + Speed);

        new Thread(() -> {
            try {
                Thread.sleep(duration);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Speed = defaultSpeed;
            System.out.println("Speed Boost terminé, retour à la vitesse normale.");
        }).start();
    }


    private Boolean isSmaller(Circle circleOne, Circle circleTwo){
        if (circleOne.getRadius() >= circleTwo.getRadius() + 2){
            return false;
        }
        return true;
    }


    public void increaseSize(double foodValue){

        setWeight(getWeight() + foodValue);
        setViewOrder(-entity.getRadius());

    }

    public void moveToward(double[] velocity) {

        // Initialiser la vélocité, qui est la position de la souris - position du joueur
        velocity = new double[]{velocity[0] - entity.getCenterX(), velocity[1] - entity.getCenterY()};

        // Utilisé pour le mouvement fluide en fonction de la distance entre la souris et le joueur.
        // Plus la souris est éloignée du cercle, plus le mouvement est rapide, etc.
        double magnitudeSmoothing = Math.sqrt( (velocity[0] * velocity[0]) + (velocity[1] * velocity[1])) / getWeight();

        // Limiter la vitesse du lissage
        if (magnitudeSmoothing > 4){
            magnitudeSmoothing = 4 * Speed;
        }

        // Normaliser la position vers laquelle le joueur se dirige pour obtenir la direction
        velocity = normalizeDouble(velocity);

        // Multiplier la direction par la vitesse pour obtenir la valeur finale de la vélocité, multiplier par le lissage pour un mouvement plus fluide
        velocity[0] *= Speed * magnitudeSmoothing;
        velocity[1] *= Speed * magnitudeSmoothing;

        // Changer la position de entity en fonction de la vélocité
        // Vérifier également si le joueur est aux limites du monde, dans ce cas il ne se déplace pas
        if (entity.getCenterX() + velocity[0] < World.getInstance().getMapLimitWidth()){
            if (entity.getCenterX() + velocity[0] > -World.getInstance().getMapLimitWidth()){
                entity.setCenterX(entity.getCenterX() + velocity[0] );
            }
        }
        if (entity.getCenterY() + velocity[1] < World.getInstance().getMapLimitHeight()){
            if (entity.getCenterY() + velocity[1] > -World.getInstance().getMapLimitHeight()){
                entity.setCenterY(entity.getCenterY() + velocity[1]);
            }
        }

    }



    public double distanceTo(double[] position){
        return Math.sqrt(Math.pow(position[0] - getPosition()[0], 2) - Math.pow(position[1] - getPosition()[1], 2) );
    }

    public double[] normalizeDouble(double[] array){
        //don't worry about it :)

        double magnitude = Math.sqrt( (array[0] * array[0]) + (array[1] * array[1]) );

        if (array[0] != 0 || array[1] != 0 ){
            return new double[]{array[0] / magnitude, array[1] / magnitude};
        }
        return new double[]{0,0};
    }


}
