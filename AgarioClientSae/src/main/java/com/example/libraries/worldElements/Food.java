
package com.example.libraries.worldElements;


import javafx.scene.Group;


public class Food extends Entity {

    public static int groupF = 100000;

    public Food(Group group, double size){
        super(group, size, groupF);
        groupF++;
        entity.setCenterX(Math.random() * (World.getInstance().getMapLimitWidth() * 2) - World.getInstance().getMapLimitWidth());
        entity.setCenterY(Math.random() * (World.getInstance().getMapLimitHeight() * 2) - World.getInstance().getMapLimitHeight());

    }
}
