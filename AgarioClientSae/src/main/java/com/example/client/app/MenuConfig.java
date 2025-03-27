package com.example.client.app;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import static com.example.client.app.HelloApplication.*;

public class MenuConfig {

    public static void openConfigMenu(Stage stage) {
        VBox menuConfig = new VBox(10);
        menuConfig.setStyle("-fx-padding: 20; -fx-background-color: #333333;");

        Label titleLabel = new Label("Configuration du jeu");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-text-fill: white;");

        // Paramètres de la carte
        HBox mapConfig = new HBox(10);
        Label mapLabel = new Label("Taille de la carte (Width x Height):");
        TextField mapWidthField = new TextField(String.valueOf(mapWidth));
        TextField mapHeightField = new TextField(String.valueOf(mapHeight));
        mapConfig.getChildren().addAll(mapLabel, mapWidthField, mapHeightField);

        // Paramètres des ennemis
        HBox enemyConfig = new HBox(10);
        Label enemyLabel = new Label("Nombre d'ennemis:");
        TextField enemyCountField = new TextField(String.valueOf(enemyCount));
        enemyConfig.getChildren().addAll(enemyLabel, enemyCountField);

        // Bouton de démarrage
        Button startButton = new Button("Démarrer le jeu");
        startButton.setOnAction(e -> {
            mapWidth = Double.parseDouble(mapWidthField.getText());
            mapHeight = Double.parseDouble(mapHeightField.getText());
            enemyCount = Integer.parseInt(enemyCountField.getText());
            startGame(stage);
        });

        menuConfig.getChildren().addAll(titleLabel, mapConfig, enemyConfig, startButton);
        scene = new Scene(menuConfig, getScreenWidth(), getScreenHeight());
        stage.setScene(scene);
    }
}
