package com.example.agarioclientsae.app;

import com.example.agarioclientsae.worldElements.World;
import com.example.agarioclientsae.factories.FactoryPlayer;
import com.example.agarioclientsae.player.Player;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
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

    static public double getScreenWidth(){
        return scene.getWindow().getWidth();
    }
    static public double getScreenHeight(){
        return scene.getWindow().getHeight();
    }

    public static void startGame(Stage stage) {
        world = World.getInstance();
        root = world.getRoot();
        FactoryPlayer factoryPlayer = new FactoryPlayer();
        player = factoryPlayer.create(root, 50);



        world.addPlayer(player);

        GameTimer timer = new GameTimer();
        timer.start();

        scene = new Scene(root, ScreenWidth, ScreenHeight);
        scene.setCamera(player.camera);

        stage.setTitle("Agar.io");
        stage.setScene(scene);

        System.out.println("Taille du monde: " + World.getMapLimitWidth() + "x" + World.getMapLimitHeight());
        stage.show();
    }



    public static void main(String[] args) {
        launch();
    }
}