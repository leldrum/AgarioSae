
package com.example.agarioclientsae.worldElements;


import com.example.agarioclientsae.worldElements.Entity;
import javafx.scene.Group;
import javafx.scene.layout.Pane;


public class Food extends Entity {


    public Food(Pane gamePane, double size){
        super(gamePane, size);
        entity.setCenterX(Math.random() * (World.getMapLimitWidth() * 2) - World.getMapLimitWidth());
        entity.setCenterY(Math.random() * (World.getMapLimitHeight() * 2) - World.getMapLimitHeight());

    }
}
