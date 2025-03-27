package com.example.libraries.worldElements;

import com.example.libraries.player.MoveableBody;
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
            player.setWeight(player.getWeight() / 2);
        }
    }


}
