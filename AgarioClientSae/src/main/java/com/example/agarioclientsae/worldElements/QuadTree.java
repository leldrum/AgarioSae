package com.example.agarioclientsae.worldElements;

import com.example.agarioclientsae.worldElements.Entity;
import javafx.scene.Group;

import java.util.ArrayList;

public class QuadTree {
    final int MAX_CAPACITY = 4;
    int level = 0;
    QuadTree northWest = null;
    QuadTree northEast = null;
    QuadTree southWest = null;
    QuadTree southEast = null;
    Boundary boundary;
    ArrayList<Entity> nodes;  // Change to store entities

    QuadTree(int level, Boundary boundary) {
        this.level = level;
        this.nodes = new ArrayList<>();
        this.boundary = boundary;
    }

    /* Traveling the Graph using Depth First Search */
    static void dfs(QuadTree tree) {
        if (tree == null)
            return;

        System.out.printf("\nLevel = %d [X1=%d Y1=%d] \t[X2=%d Y2=%d] ",
                tree.level, tree.boundary.getxMin(), tree.boundary.getyMin(),
                tree.boundary.getxMax(), tree.boundary.getyMax());

        for (Entity node : tree.nodes) {
            System.out.printf(" \n\t  x=%d y=%d", (int) node.entity.getCenterX(), (int) node.entity.getCenterY());
        }
        if (tree.nodes.size() == 0) {
            System.out.printf(" \n\t  Leaf Node.");
        }
        dfs(tree.northWest);
        dfs(tree.northEast);
        dfs(tree.southWest);
        dfs(tree.southEast);
    }

    void split() {
        int xOffset = this.boundary.getxMin() + (this.boundary.getxMax() - this.boundary.getxMin()) / 2;
        int yOffset = this.boundary.getyMin() + (this.boundary.getyMax() - this.boundary.getyMin()) / 2;

        // Création des 4 sous-quadrants
        northWest = new QuadTree(this.level + 1, new Boundary(this.boundary.getxMin(), this.boundary.getyMin(), xOffset, yOffset));
        northEast = new QuadTree(this.level + 1, new Boundary(xOffset, this.boundary.getyMin(), this.boundary.getxMax(), yOffset));
        southWest = new QuadTree(this.level + 1, new Boundary(this.boundary.getxMin(), yOffset, xOffset, this.boundary.getyMax()));
        southEast = new QuadTree(this.level + 1, new Boundary(xOffset, yOffset, this.boundary.getxMax(), this.boundary.getyMax()));


        ArrayList<Entity> tempNodes = new ArrayList<>(nodes);
        nodes.clear();


        for (Entity entity : tempNodes) {
            System.out.println("Redistributing entity (" + (int) entity.entity.getCenterX() + "," +
                    (int) entity.entity.getCenterY() + ") after split...");

            // Insert dans le quadrant approprié
            if (this.northWest.boundary.inRange((int) entity.entity.getCenterX(), (int) entity.entity.getCenterY())) {
                this.northWest.insert((int) entity.entity.getCenterX(), (int) entity.entity.getCenterY(), entity);
            } else if (this.northEast.boundary.inRange((int) entity.entity.getCenterX(), (int) entity.entity.getCenterY())) {
                this.northEast.insert((int) entity.entity.getCenterX(), (int) entity.entity.getCenterY(), entity);
            } else if (this.southWest.boundary.inRange((int) entity.entity.getCenterX(), (int) entity.entity.getCenterY())) {
                this.southWest.insert((int) entity.entity.getCenterX(), (int) entity.entity.getCenterY(), entity);
            } else if (this.southEast.boundary.inRange((int) entity.entity.getCenterX(), (int) entity.entity.getCenterY())) {
                this.southEast.insert((int) entity.entity.getCenterX(), (int) entity.entity.getCenterY(), entity);
            }
        }
    }



    // Modified to insert Entity objects
    void insert(int x, int y, Entity entity) {
        if (!this.boundary.inRange(x, y)) {
            System.out.println("Entity (" + x + "," + y + ") is out of bounds");
            return;
        }

        if (nodes.size() < MAX_CAPACITY) {
            nodes.add(entity);
            System.out.println("Added entity at (" + x + "," + y + ") to level " + this.level);
            return;
        }

        if (northWest == null) {
            System.out.println("Splitting at level " + this.level);
            split();
        }

        if (this.northWest.boundary.inRange(x, y)) {
            this.northWest.insert(x, y, entity);
        } else if (this.northEast.boundary.inRange(x, y)) {
            this.northEast.insert(x, y, entity);
        } else if (this.southWest.boundary.inRange(x, y)) {
            this.southWest.insert(x, y, entity);
        } else if (this.southEast.boundary.inRange(x, y)) {
            this.southEast.insert(x, y, entity);
        } else {
            System.out.printf("ERROR: Unhandled partition for (%d, %d)\n", x, y);
        }
    }


    public static void main(String args[]) {
        double mapLimitWidth = 700;
        double mapLimitHeight = 700;

        // Définir un espace pour le QuadTree
        Boundary boundary = new Boundary(0, 0, (int) mapLimitWidth, (int) mapLimitHeight);
        QuadTree quadTree = new QuadTree(0, boundary);

        // Création des entités
        Group root = new Group();
        Entity entity1 = new Enemy(root, 50);
        entity1.entity.setCenterX(100);
        entity1.entity.setCenterY(100);

        Entity entity2 = new Enemy(root, 50);
        entity2.entity.setCenterX(200);
        entity2.entity.setCenterY(200);

        Entity entity3 = new Enemy(root, 50);
        entity3.entity.setCenterX(600);
        entity3.entity.setCenterY(600);

        Entity entity4 = new Enemy(root, 50);
        entity4.entity.setCenterX(500);
        entity4.entity.setCenterY(300);

        Entity entity5 = new Enemy(root, 50);
        entity5.entity.setCenterX(250);
        entity5.entity.setCenterY(250);

        // Ajout manuel des entités dans le QuadTree
        quadTree.insert((int) entity1.entity.getCenterX(), (int) entity1.entity.getCenterY(), entity1);
        quadTree.insert((int) entity2.entity.getCenterX(), (int) entity2.entity.getCenterY(), entity2);
        quadTree.insert((int) entity3.entity.getCenterX(), (int) entity3.entity.getCenterY(), entity3);
        quadTree.insert((int) entity4.entity.getCenterX(), (int) entity4.entity.getCenterY(), entity4);
        quadTree.insert((int) entity5.entity.getCenterX(), (int) entity5.entity.getCenterY(), entity5); // Déclenche le split

        // Vérification du QuadTree
        QuadTree.dfs(quadTree);
    }



}
