package com.example.agarioclientsae;

import javafx.scene.Group;

import java.util.ArrayList;

public class World {
    private static double mapLimitWidth = 2000;
    private static double mapLimitHeight = 2000;

    public static Group root = new Group();

    public static int enemies = 0;

    private ArrayList<Entity> entities;

    private ArrayList<Player> players = new ArrayList<Player>();

    private static ArrayList queuedObjectsForDeletion = new ArrayList<>();

    public int maxTimer = 2;
    public int timer = maxTimer;

    public World(Group root){
        this.root = root;
    }

    static public double getMapLimitWidth(){
        return mapLimitWidth;
    }
    static public double getMapLimitHeight(){
        return mapLimitHeight;
    }

    public void Update(){
        if (timer <= 0){
            if (root.getChildren().size() < 200){
                createFood();
            }

            timer = maxTimer; //reset the timer
        }

        if (enemies < 5){
            Enemy enemy = new Enemy(root, 50);
            enemies++;
        }

        timer--; //decrement timer

    }

    public ArrayList<Player> getPlayers(){
        return this.players;
    }

    public void addPlayer(Player p){
        players.add(p);
    }

    public void createFood(){
        Food food = new Food(root, 10);
    }

    public static void queueFree(Object object){
        //there are errors when deleting objects inbetween of frames, mostly just unsafe in general
        //so when you want to delete an object, reference AgarioApplication and call this function queueFree
        //e.g. AgarioApplication.queueFree(foodSprite);
        //puts objects in a dynamic array, just means an array that doesnt have a fixed size
        //every frame before the update function is called, the objects in the queue will be deleted
        queuedObjectsForDeletion.add(object);
        Entity entity = (Entity) object;
        entity.onDeletion();
        enemies--;
    }

    public void freeQueuedObjects(){
        //deletes all objects in the queue
        //complicated to explain why we have to do it this way
        //just know if we dont, there will be tons of lag and errors every frame
        root.getChildren().removeAll(queuedObjectsForDeletion);
        queuedObjectsForDeletion.clear();

    }
}
