package com.example.libraries.models.factories;
import javafx.scene.Group;

public interface Factory<Entity> {
    Object create(double x, double y, double weight);
}
