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

    // Paramètres modifiables
    public static double mapWidth = 2000;
    public static double mapHeight = 2000;
    public static int enemyCount = 5;

    @Override
    public void start(Stage stage) throws IOException {

        MenuStart menu = new MenuStart(stage);
        scene = new Scene(menu, ScreenWidth, ScreenHeight);


        stage.setTitle("Agar.io");
        stage.setScene(scene);

        stage.show();
    }

    static public double[] getMousePosition() {
        java.awt.Point mouse = java.awt.MouseInfo.getPointerInfo().getLocation();
        Point2D mousePos = root.screenToLocal(mouse.x, mouse.y);
        return new double[]{mousePos.getX(), mousePos.getY()};
    }

    public static double getScreenWidth() {
        return scene.getWindow().getWidth();
    }

    public static double getScreenHeight() {
        return scene.getWindow().getHeight();
    }

    public static void startGame(Stage stage) {
        System.out.println("Démarrage du jeu");

        // Initialisation du monde
        world = World.getInstance();
        root = World.getRoot();
        world.setMapLimitWidth(mapWidth);
        world.setMapLimitHeight(mapHeight);
        world.setEnemiesMax(enemyCount);
        root = World.getInstance().getRoot();

        // Création du joueur
        FactoryPlayer factoryPlayer = new FactoryPlayer();
        player = factoryPlayer.create(root, 50);

        if (player == null) {
            System.err.println("ERREUR : Le joueur n'a pas été créé");
            return;
        }


        world.addPlayer(player);

        timer = new GameTimer();
        timer.start();
        // Gestion de la minimap

        // Création de la scène
        scene = new Scene(root, ScreenWidth, ScreenHeight);
        scene.setCamera(player.getCamera());
        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case SPACE:
                    player.divide();
                    System.out.println("Barre espace appuyée");
                    break;
                    case V :
                        player.Union();
                        System.out.println("V appuyée");
                        break;
                default:
                    break;
            }
        });

        // Affichage
        stage.setTitle("Agar.io");
        stage.setScene(scene);


        stage.setResizable(false);
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