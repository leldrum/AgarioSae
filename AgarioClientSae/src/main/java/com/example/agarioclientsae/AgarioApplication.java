package com.example.agarioclientsae;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;

import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.io.IOException;

public class AgarioApplication extends Application {

    public static boolean gameStarted = false;
    public static Player player;
    private static Scene scene;

    public static Group root = new Group();

    private static double mapLimitWidth = 2000;
    private static double mapLimitHeight = 2000;

    @Override
    public void start(Stage stage) throws IOException {

        MenuStart menu = new MenuStart(stage);
        Scene scene = new Scene(menu, 1280, 720);
        stage.setTitle("Bienvenu sur Agar.io !");
        stage.setScene(scene);
        stage.show();
    }

    public static void startGame(Stage stage){

        player = new FactoryPlayer(50).create();


        scene = new Scene(root, 1280, 720);
        scene.setCamera(player.camera);
        stage.setScene(scene);


    }

    static public double getScreenWidth(){
        return scene.getWindow().getWidth();
    }
    static public double getScreenHeight(){
        return scene.getWindow().getHeight();
    }
    static public double getMapLimitWidth(){
        return mapLimitWidth;
    }
    static public double getMapLimitHeight(){
        return mapLimitHeight;
    }


    public static void main(String[] args) {
        launch();
    }

    public void createFood(){
        Food food = new FactoryFood(10).create();
    }
}