package com.example.libraries.factories;

import javafx.scene.Group;

public interface Factory<Entity> {
    Object create(Group group, double weight);
}
