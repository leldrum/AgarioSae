package com.example.agarioclientsae.factories;

import javafx.scene.Group;
import javafx.scene.layout.Pane;

import javax.swing.*;

public interface Factory<Entity> {

    Entity create(Pane gamePane, double weight);
}
