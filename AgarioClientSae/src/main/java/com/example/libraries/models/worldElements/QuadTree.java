package com.example.libraries.models.worldElements;

import java.util.ArrayList;

public class QuadTree {
    final int MAX_CAPACITY = 5;
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
            /*System.out.println("Redistributing entity (" + (int) entity.getPosition()[0] + "," +
                    (int) entity.getPosition()[1] + ") after split...");*/

            // Insert dans le quadrant approprié
            if (this.northWest.boundary.inRange((int) entity.getPosition()[0], (int) entity.getPosition()[1])) {
                this.northWest.insert((int) entity.getPosition()[0], (int) entity.getPosition()[1], entity);
            } else if (this.northEast.boundary.inRange((int) entity.getPosition()[0], (int) entity.getPosition()[1])) {
                this.northEast.insert((int) entity.getPosition()[0], (int) entity.getPosition()[1], entity);
            } else if (this.southWest.boundary.inRange((int) entity.getPosition()[0], (int) entity.getPosition()[1])) {
                this.southWest.insert((int) entity.getPosition()[0], (int) entity.getPosition()[1], entity);
            } else if (this.southEast.boundary.inRange((int) entity.getPosition()[0], (int) entity.getPosition()[1])) {
                this.southEast.insert((int) entity.getPosition()[0], (int) entity.getPosition()[1], entity);
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
