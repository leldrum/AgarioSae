package com.example.agarioclientsae.factories;

import javafx.scene.Group;

import javax.swing.*;

public interface Factory<Entity> {
    Object create(Group group, double weight);
}
