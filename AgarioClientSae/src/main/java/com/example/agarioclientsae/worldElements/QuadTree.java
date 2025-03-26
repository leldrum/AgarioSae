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

        /*System.out.printf("\nLevel = %d [X1=%d Y1=%d] \t[X2=%d Y2=%d] ",
                tree.level, tree.boundary.getxMin(), tree.boundary.getyMin(),
                tree.boundary.getxMax(), tree.boundary.getyMax());*/

        for (Entity node : tree.nodes) {
            /*System.out.printf(" \n\t  x=%d y=%d", (int) node.entity.getCenterX(), (int) node.entity.getCenterY());*/
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
            /*System.out.println("Redistributing entity (" + (int) entity.entity.getCenterX() + "," +
                    (int) entity.entity.getCenterY() + ") after split...");*/

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
            /*System.out.println("Entity (" + x + "," + y + ") is out of bounds");*/
            return;
        }

        if (nodes.size() < MAX_CAPACITY) {
            nodes.add(entity);
            //System.out.println("Added entity at (" + x + "," + y + ") to level " + this.level);
            return;
        }

        if (northWest == null) {
            //System.out.println("Splitting at level " + this.level);
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
            //System.out.printf("ERROR: Unhandled partition for (%d, %d)\n", x, y);
        }
    }






}
