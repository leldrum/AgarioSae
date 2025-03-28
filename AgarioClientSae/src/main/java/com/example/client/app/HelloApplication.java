package com.example.client.app;

import com.example.client.controllers.MoveableBodyController;
import com.example.client.controllers.PlayableGroupController;
import com.example.client.controllers.WorldController;
import com.example.client.views.*;
import com.example.libraries.models.player.PlayerModel;
import com.example.libraries.models.factories.FactoryPlayer;
import com.example.libraries.models.worldElements.*;
import com.example.libraries.models.worldElements.WorldModel;
import com.example.client.views.WorldView;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class HelloApplication extends Application {

    private static double ScreenWidth = 1280;
    private static double ScreenHeight = 720;

    public static Scene scene;
    public static Group root;
    public static Boolean gameStarted = false;
    public static PlayerModel player;
    public static GameTimer timer;
    private static WorldView worldView;
    private static WorldModel world;
    private static WorldController controller;

    @Override
    public void start(Stage stage) throws IOException {
        MenuStart menu = new MenuStart(stage);
        scene = new Scene(menu, ScreenWidth, ScreenHeight);
        stage.setTitle("Agar.io");
        stage.setScene(scene);
        stage.show();
    }

    public static double[] getMousePosition() {
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

        world = WorldModel.getInstance();
        root = new Group();
        FactoryPlayer factoryPlayer = new FactoryPlayer();
        Random rand = new Random();
        double x = rand.nextDouble() * world.getMapWidth();
        double y = rand.nextDouble() * world.getMapHeight();
        player = factoryPlayer.create(650, 350, 20);
        world.addEntity(player);


        PlayableGroupView pv = new PlayableGroupView(player);
        PlayableGroupController pc = new PlayableGroupController(player, pv);





        world = WorldModel.getInstance();
        world.spawnFood(200); // Générer 100 pastilles de nourriture

        EntitiesView entityView = new EntitiesView((ArrayList<Entity>) world.getEntities());
        //entityView.addEntityGraphics(player);


        entityView.updateView();



            MoveableBodyController mbc = new MoveableBodyController(world.getEntities());
            //root.getChildren().add(Objects.requireNonNull(entityView.getSprite(player)));

            worldView = new WorldView(root);
            controller = new WorldController(world, worldView,pc,entityView,mbc);



        // Initialisation de la scène
        scene = new Scene(root, ScreenWidth, ScreenHeight);
        stage.setScene(scene);
        stage.show();

        // Boucle de jeu
        timer = new GameTimer(controller,pc);
        timer.start();

        System.out.println(world.getEntities());
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
