package com.example.client.app;

import com.example.client.controllers.MoveableBodyController;
import com.example.client.controllers.PlayableGroupController;
import com.example.client.controllers.WorldController;
import com.example.client.views.*;
import com.example.libraries.models.player.PlayerModel;
import com.example.libraries.models.factories.FactoryPlayer;
import com.example.libraries.models.worldElements.*;
import com.example.libraries.models.worldElements.WorldModel;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
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
    private static WorldController worldController;
    private static PlayableGroupController playableGroupController;

    private static EntityView entityView;

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
        worldView = new WorldView(root);
        worldController = new WorldController(world, worldView);


        // Création du joueur
        FactoryPlayer factoryPlayer = new FactoryPlayer();
        Random rand = new Random();
        double x = rand.nextDouble() * world.getMapWidth();
        double y = rand.nextDouble() * world.getMapHeight();
        player = factoryPlayer.create(0, 0, 50);
        world.addEntity(player);
        PlayableGroupView playerView = new PlayableGroupView(player);
        MoveableBodyView mvview = new MoveableBodyView(player);
        MoveableBodyController mv = new MoveableBodyController(player,mvview);
        entityView = new EntityView(player);
        playableGroupController = new PlayableGroupController(player,playerView,mv,entityView);

        // Ajout des entités visuelles
        for (Entity entity : world.getEntities()) {
            if(entity instanceof Food) {
                FoodView foodView = new FoodView((Food) entity);
                root.getChildren().add(foodView);
            }
            if(entity instanceof PlayerModel) {
                PlayableGroupView player = new PlayableGroupView((PlayerModel) entity);
                root.getChildren().add(player);
            }

            if(entity instanceof EnemyModel) {
                EnemyView enemyView = new EnemyView((EnemyModel) entity);
                root.getChildren().add(enemyView.getSprite());
            }
            EntityView entityView = new EntityView(entity);
            root.getChildren().add(entityView);
        }


        // Initialisation de la scène
        scene = new Scene(root, ScreenWidth, ScreenHeight);
        stage.setScene(scene);
        stage.show();

        // Boucle de jeu
        timer = new GameTimer(worldController, playableGroupController);
        timer.start();

        System.out.println(world.getEntities());
        System.out.println("Group : " + root.getChildren());
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
