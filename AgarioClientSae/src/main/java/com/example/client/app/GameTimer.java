package com.example.client.app;

import com.example.client.controllers.PlayableGroupController;
import com.example.client.controllers.WorldController;
import javafx.animation.AnimationTimer;

public class GameTimer extends AnimationTimer {

    private final WorldController worldController;

    private final PlayableGroupController pc;
    private double framesPerSecond = 30;
    private double interval = 1000000000 / framesPerSecond;
    private double last = 0;

    public GameTimer(WorldController controller, PlayableGroupController pc){
        this.worldController = controller;
        this.pc = pc;
    }

    @Override
    public void handle(long now) {
        if (last == 0) {
            last = now;
        }

        if (now - last > interval) {
            last = now;

            worldController.update(); // Met à jour le monde et libère les objets supprimés
            pc.update();
        }
    }
}
