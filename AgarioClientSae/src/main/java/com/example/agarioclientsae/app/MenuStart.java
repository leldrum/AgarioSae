package com.example.agarioclientsae.app;

import com.example.agarioclientsae.server.ClientServer;
import javafx.scene.control.Label;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.control.TextField;

import java.awt.*;

public class MenuStart extends VBox {

    Stage stage;
    TextField ipField;
    TextField  portField;
    Button connectButton;


    public MenuStart(Stage stage) {
        this.stage = stage;


        Label label = new Label("Agar.io");
        label.setFont(new Font("Arial", 60));


        Button localButton = new Button("Jouer en local");
        Button onlineButton = new Button("Jouer en ligne");
        Button exitButton = new Button("Fermer");


        // Champs pour l'IP et le port (cachés par défaut)
        ipField = new TextField();
        ipField.setMaxWidth(300);
        ipField.setPromptText("Adresse IP du serveur");
        ipField.setVisible(false);

        portField = new TextField();
        portField.setMaxWidth(300);
        portField.setPromptText("Port");
        portField.setVisible(false);

        // Bouton pour se connecter au serveur
        connectButton = new Button("Se connecter");
        connectButton.setVisible(false);  // Initialement invisible
        connectButton.setOnAction(e -> handleConnectButton());



        // Actions des boutons
        localButton.setOnAction(e -> handleStartButton());
        onlineButton.setOnAction(e -> handleOnlineButton());
        exitButton.setOnAction(e -> handleExitButton());

        setSpacing(40);
        setAlignment(Pos.CENTER);


        getChildren().addAll(label, localButton, exitButton, onlineButton);
    }


    private void handleStartButton() {
        // Implement logic for start button click
        System.out.println("Local button clicked");
        HelloApplication.gameStarted = true;
        HelloApplication.startGame(stage);
    }

    private void handleOnlineButton() {
        // Affichage des champs IP, Port et bouton "Se connecter"
        System.out.println("Online button clicked");

        ipField.setVisible(true);
        portField.setVisible(true);
        connectButton.setVisible(true); // Rendre visible le bouton "Se connecter"
        getChildren().addAll(ipField, portField, connectButton);
    }

    private void handleConnectButton() {

        // Récupérer l'adresse IP et le port
        String serverIp = ipField.getText();
        String portText = portField.getText();

        // Validation des champs
        if (serverIp.isEmpty() || portText.isEmpty()) {
            System.out.println("L'adresse IP ou le port est vide.");
            // Vous pouvez afficher un message d'erreur ici si nécessaire.
        }

        // Convertir le port en entier
        int port;
        try {
            port = Integer.parseInt(portText);
        } catch (NumberFormatException e) {
            System.out.println("Le port n'est pas valide.");
            return; // Afficher un message d'erreur si le port n'est pas valide
        }

        // Créer une instance du ClientServer et se connecter
        System.out.println("Connexion au serveur " + serverIp + ":" + port);
        ClientServer clientServer = new ClientServer(serverIp, port);

    }


    private void handleExitButton() {
        stage.close();
        System.out.println("Exit button clicked");
    }

}