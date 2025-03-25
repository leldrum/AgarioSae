
package com.example.agarioclientsae;


import javafx.scene.Group;
import javafx.scene.shape.Circle;


public class Food extends Entity{


    Food(Group group, double size){
        super(group, size);
        entity.setCenterX(Math.random() * (World.getMapLimitWidth() * 2) - World.getMapLimitWidth());
        entity.setCenterY(Math.random() * (World.getMapLimitHeight() * 2) - World.getMapLimitHeight());

    }
}
