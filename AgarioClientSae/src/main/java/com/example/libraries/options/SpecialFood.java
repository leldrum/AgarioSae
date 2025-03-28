package com.example.libraries.options;

import com.example.libraries.player.MoveableBody;
import com.example.libraries.worldElements.Food;
import javafx.scene.Group;
import javafx.scene.paint.Color;

public class SpecialFood extends Food {
    private boolean givesSpeedBonus;
    private double speedMultiplier;
    private int effectDuration;

    public SpecialFood(Group group, double weight, boolean givesSpeedBonus, double speedMultiplier, int duration) {
        super(group, weight);
        this.givesSpeedBonus = givesSpeedBonus;
        this.speedMultiplier = speedMultiplier;
        this.effectDuration = duration;
        entity.setFill(Color.RED);
    }

    public void applyEffect(MoveableBody player) {
        if (givesSpeedBonus) {
            player.applySpeedBoost(speedMultiplier, effectDuration);
        }
        else {
            if(player.getWeight() > 40) {
                player.setWeight(player.getWeight() / 2);
            }
        }
    }


}
