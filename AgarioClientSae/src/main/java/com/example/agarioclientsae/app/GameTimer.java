package com.example.agarioclientsae.app;

import com.example.agarioclientsae.worldElements.Entity;
import com.example.agarioclientsae.worldElements.World;
import javafx.animation.AnimationTimer;
import javafx.scene.Node;

public class GameTimer extends AnimationTimer {

    private double framesPerSecond = 30;
    private double interval = 1000000000 / framesPerSecond;
    private double last = 0;

    private World world;

    public GameTimer(){
        this.world = World.getInstance();
    }

    @Override
    public void handle(long now) {
        if (last == 0) last = now;

        if (now - last > interval ){
            last = now;
            System.out.println("GameTimer appelé");

            world.freeQueuedObjects();
            for (Node entity : world.gamePane.getChildren()){
                if (entity instanceof Entity) {
                    ((Entity) entity).Update();
                } else {
                    System.out.println("Attention : " + entity + " n'est pas une Entity !");
                }
            }
            world.Update();
        }
    }

}
