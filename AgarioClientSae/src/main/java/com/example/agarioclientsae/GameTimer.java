package com.example.agarioclientsae;

import javafx.animation.AnimationTimer;
import javafx.scene.Node;

public class GameTimer extends AnimationTimer {

    private double framesPerSecond = 60;
    private double interval = 1000000000 / framesPerSecond;
    private double last = 0;

    private World world;

    public GameTimer(World world){
        this.world = world;
    }
    @Override
    public void handle(long now) {

        if (last == 0){
            last = now;
        }

        if (now - last > interval ){
            last = now;
            world.freeQueuedObjects(); // deletes any objects queued up to be free
            for (Node entity : world.root.getChildren()){
                Entity convertedEntity = (Entity) entity;
                convertedEntity.Update(world);
            }
            world.Update(); //calls update function every frame


        }
    }
}
