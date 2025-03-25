package com.example.agarioclientsae;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    private static double ScreenWidth = 1280;
    private static double ScreenHeight = 720;

    private static Scene scene;

    private static World world;

    public static Group root;

    public static Boolean gameStarted = false;



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
        Player player = new Player(root, 50);

        world.addPlayer(player);

        GameTimer timer = new GameTimer(world);
        timer.start();

        scene = new Scene(root, ScreenWidth, ScreenHeight, Paint.valueOf("afafaf"));
        scene.setCamera(player.camera);

        stage.setTitle("Agar.io");
        stage.setScene(scene);

        stage.show();
    }



    public static void main(String[] args) {
        launch();
    }
}