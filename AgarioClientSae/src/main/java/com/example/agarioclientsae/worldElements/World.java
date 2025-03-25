package com.example.agarioclientsae.worldElements;

import com.example.agarioclientsae.worldElements.Entity;
import com.example.agarioclientsae.factories.FactoryEnemy;
import com.example.agarioclientsae.factories.FactoryFood;
import com.example.agarioclientsae.player.MoveableBody;
import com.example.agarioclientsae.player.Player;
import javafx.scene.Group;
import javafx.scene.Node;

import java.util.ArrayList;

public class World {
    private static double mapLimitWidth = 3000;
    private static double mapLimitHeight = 3000;

    private int enemySpawnTimer = 100;
    private int enemySpawnRate = 100;

    public static Group root = new Group();

    public static int enemies = 0;

    private ArrayList<Entity> entities = new ArrayList<>();

    private Player player;

    private static ArrayList<Object> queuedObjectsForDeletion = new ArrayList<>();

    public int maxTimer = 2;
    public int timer = maxTimer;

    private static World instance = new World();

    private QuadTree quadTree;

    private World(){}

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
            timer = maxTimer;
        }

        if (enemies < 5 && enemySpawnTimer <= 0){
            FactoryEnemy factoryEnemy = new FactoryEnemy();
            Enemy enemy = factoryEnemy.create(root, 50);
            enemies++;
            enemySpawnTimer = enemySpawnRate;
        }

        enemySpawnTimer--;

        timer--;

        updateQuadTreeEntities();
    }

    public static World getInstance(){
        return instance;
    }

    public static Group getRoot() {
        return root;
    }

    public Player getPlayer(){
        return this.player;
    }

    public void addPlayer(Player p){
        player = p;
    }

    public void addEntity(Entity entity) {
        entities.add(entity);
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }

    public void createFood(){
        FactoryFood factoryFood  = new FactoryFood ();
        Food food = factoryFood.create(root, 10);

    }

    public void reset(){
        instance = new World();
    }

    public static void queueFree(Object object){
        queuedObjectsForDeletion.add(object);
        Entity entity = (Entity) object;
        entity.onDeletion();
        enemies--;
        // Retirer de l'arbre QuadTree

    }


    public void freeQueuedObjects(){

        root.getChildren().removeAll(queuedObjectsForDeletion);
        queuedObjectsForDeletion.clear();
    }

    private void updateEntities(){

        for (Node entity : root.getChildren()){
            if (entity instanceof MoveableBody){
                MoveableBody moveableEntity = (MoveableBody) entity;
                moveableEntity.checkCollision();
            }
        }
    }

    void updateQuadTreeEntities(){
        quadTree = new QuadTree(0, new Boundary(0, 0, (int) mapLimitWidth, (int) mapLimitHeight));

        for (Entity entity : entities) {
            int x = (int) entity.entity.getCenterX();
            int y = (int) entity.entity.getCenterY();
            quadTree.insert(x, y, entity);
        }
    }
}
