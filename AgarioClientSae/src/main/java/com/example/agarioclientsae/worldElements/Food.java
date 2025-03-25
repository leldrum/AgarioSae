
package com.example.agarioclientsae.worldElements;


import com.example.agarioclientsae.worldElements.Entity;
import javafx.scene.Group;


public class Food extends Entity {


    public Food(Group group, double size){
        super(group, size);
        entity.setCenterX(Math.random() * (World.getMapLimitWidth() * 2) - World.getMapLimitWidth());
        entity.setCenterY(Math.random() * (World.getMapLimitHeight() * 2) - World.getMapLimitHeight());

    }
}
