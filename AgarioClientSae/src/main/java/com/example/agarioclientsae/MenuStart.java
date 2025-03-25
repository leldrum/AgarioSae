package com.example.agarioclientsae;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.application.Application;
import javafx.scene.control.Label;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.FileInputStream;

public class MenuStart extends VBox {

    Stage stage;
    public MenuStart(Stage stage) {
        this.stage = stage;

        Label label = new Label("Agar.io");
        label.setFont(new Font("Arial", 60));


        Button startButton = new Button("Démarrer");
        Button exitButton = new Button("Fermer");


        startButton.setOnAction(e -> handleStartButton());
        exitButton.setOnAction(e -> handleExitButton());


        setSpacing(40);
        setAlignment(Pos.CENTER);


        getChildren().addAll(label, startButton, exitButton);
    }


    private void handleStartButton() {
        // Implement logic for start button click
        System.out.println("Start button clicked");
        AgarioApplication.gameStarted = true;
        AgarioApplication.startGame(stage);
    }


    private void handleExitButton() {
        // Implement logic for exit button click
        System.out.println("Exit button clicked");
    }

}