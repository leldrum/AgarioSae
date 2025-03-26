package agarioserversae.app;

import javafx.scene.control.Label;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

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
        HelloApplication.gameStarted = true;
        HelloApplication.startGame(stage);
    }


    private void handleExitButton() {
        // Implement logic for exit button click
        System.out.println("Exit button clicked");
    }

}