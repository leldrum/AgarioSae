package com.example.agarioclientsae;

import javafx.scene.Group;

import javax.swing.*;

public interface Factory<Entity> {

    Entity create(Group group, double weight);
}
