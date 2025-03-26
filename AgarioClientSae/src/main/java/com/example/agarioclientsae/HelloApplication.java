package com.example.agarioclientsae;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;

public class HelloApplication extends Application {

    private static double ScreenWidth = 1280;
    private static double ScreenHeight = 720;

    private static Scene scene;

    public static World world;

    public static Group root;
    public static Pane uiLayer; // Un calque pour les éléments d'interface


    public static Pane gamePane;

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

    static public double getScreenWidth(){
        return scene.getWindow().getWidth();
    }
    static public double getScreenHeight(){
        return scene.getWindow().getHeight();
    }

    public static Minimap minimap; // Ajouter la minimap

    public static void startGame(Stage stage) {
        world = World.getInstance();
        root = world.getRoot();
        FactoryPlayer factoryPlayer = new FactoryPlayer();
        player = factoryPlayer.create(root, 50);

        // Calque UI qui restera fixe
        Pane uiLayer = new Pane();
        uiLayer.setPickOnBounds(false);

        // Ajouter la minimap
        minimap = new Minimap(150, 150, (world.getMapLimitWidth() * world.getMapLimitHeight()));
        Canvas minimapCanvas = minimap.getCanvas();
        minimapCanvas.setManaged(false); // Important pour éviter les redimensionnements automatiques
        minimapCanvas.setTranslateX(ScreenWidth - 160); // Positionner en haut à droite
        minimapCanvas.setTranslateY(10);

        uiLayer.getChildren().add(minimapCanvas);

        // Ajouter root (jeu) et uiLayer (interface) dans un Group
        Group rootLayer = new Group();
        rootLayer.getChildren().add(root);  // Jeu
        rootLayer.getChildren().add(uiLayer);  // UI fixe

        world.addPlayer(player);

        GameTimer timer = new GameTimer();
        timer.start();

        scene = new Scene(rootLayer, ScreenWidth, ScreenHeight);
        scene.setCamera(player.camera);

        stage.setTitle("Agar.io");
        stage.setScene(scene);
        stage.show();
    }






    public static void main(String[] args) {
        launch();
    }
}