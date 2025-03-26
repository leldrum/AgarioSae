package com.example.client.app;

import com.example.libraries.player.PlayableGroup;
import com.example.libraries.worldElements.World;
import com.example.libraries.factories.FactoryPlayer;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    private static double ScreenWidth = 1280;
    private static double ScreenHeight = 720;

    public static Scene scene;

    public static World world;

    public static Group root;

    public static Boolean gameStarted = false;

    public static PlayableGroup player;

    public static GameTimer timer;





    @Override
    public void start(Stage stage) throws IOException {

        MenuStart menu = new MenuStart(stage);
        scene = new Scene(menu, ScreenWidth, ScreenHeight);


        stage.setTitle("Agar.io");
        stage.setScene(scene);

        stage.show();
    }

    static public double[] getMousePosition(){
        java.awt.Point mouse = java.awt.MouseInfo.getPointerInfo().getLocation();
        Point2D mousePos = root.screenToLocal(mouse.x, mouse.y);
        return new double[]{mousePos.getX(), mousePos.getY()};
    }

    public static double getScreenWidth(){
        return scene.getWindow().getWidth();
    }
    public static double getScreenHeight(){
        return scene.getWindow().getHeight();
    }

    public static void startGame(Stage stage) {
        System.out.println("Démarrage du jeu");

        // Initialisation du monde
        world = World.getInstance();
        root = World.getRoot();

        // Création du joueur
        FactoryPlayer factoryPlayer = new FactoryPlayer();
        player = factoryPlayer.create(root, 30);

        if (player == null) {
            System.err.println("ERREUR : Le joueur n'a pas été créé");
            return;
        }


        world.addPlayer(player);

        timer = new GameTimer();
        timer.start();
        // Gestion de la minimap
        Canvas minimapCanvas = world.getMinimapCanvas();
        if (minimapCanvas != null) {
            // Nettoie le root en gardant seulement la minimap
            //root.getChildren().retainAll(minimapCanvas);
            // Réajoute le joueur
            System.out.println("Joueur ajouté au root : " + player.parts.get(0).part.entity);
            root.getChildren().add(player.parts.get(0).part.entity);

        }

        // Mise à jour de la minimap
        world.updateMinimap();

        // Création de la scène
        scene = new Scene(root, ScreenWidth, ScreenHeight);
        scene.setCamera(player.getCamera());
        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case SPACE:
                    player.divide();
                    System.out.println("Barre espace appuyée");
                    break;
                default:
                    break;
            }
        });

        // Affichage
        stage.setTitle("Agar.io");
        stage.setScene(scene);

        System.out.println("Taille du monde: " + World.getMapLimitWidth() + "x" + World.getMapLimitHeight());
        stage.show();
    }

    public static void startGameClient(Stage stage) {
        if (gameStarted) {
            System.out.println("Le jeu est déjà en cours.");
            return;
        }
        gameStarted = true;
        startGame(stage);

    }


    public static void main(String[] args) {
        launch();
    }
}