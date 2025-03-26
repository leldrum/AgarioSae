package com.example.agarioclientsae.app;

import com.example.agarioclientsae.worldElements.World;
import com.example.agarioclientsae.factories.FactoryPlayer;
import com.example.agarioclientsae.player.Player;
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

    private static Scene scene;

    public static World world;

    public static Group root;

    public static Boolean gameStarted = false;

    public static Player player;


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
        player = factoryPlayer.create(root, 50);

        if (player == null) {
            System.err.println("ERREUR : Le joueur n'a pas été créé");
            return;
        }

        // Configuration initiale
        world.addPlayer(player);
        //world.addEntity(player);

        // Gestion de la minimap
        Canvas minimapCanvas = world.getMinimapCanvas();
        if (minimapCanvas != null) {
            // Nettoie le root en gardant seulement la minimap
            //root.getChildren().retainAll(minimapCanvas);
            // Réajoute le joueur
            root.getChildren().add(player.entity);
            System.out.println("Joueur ajouté au root : " + player.entity);
        }

        // Mise à jour de la minimap
        world.updateMinimap();

        // Création de la scène
        scene = new Scene(root, ScreenWidth, ScreenHeight);
        scene.setCamera(player.camera);

        // Affichage
        stage.setTitle("Agar.io");
        stage.setScene(scene);
        stage.show();

        // Lancement du jeu
        GameTimer timer = new GameTimer();
        timer.start();

    }



    public static void main(String[] args) {
        launch();
    }
}