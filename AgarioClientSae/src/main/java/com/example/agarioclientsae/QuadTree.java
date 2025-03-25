package com.example.agarioclientsae;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.Group;

public class QuadTree {
    private final int MAX_CAPACITY = 10;
    private int level;
    private List<Entity> entities;
    private QuadTree northWest, northEast, southWest, southEast;
    private Boundary boundary;

    public QuadTree(int level, Boundary boundary) {
        this.level = level;
        this.boundary = boundary;
        this.entities = new ArrayList<>();
        this.northWest = this.northEast = this.southWest = this.southEast = null;
    }

    // Splitting the space into 4 quadrants
    private void split() {
        int xOffset = this.boundary.getxMin() + (this.boundary.getxMax() - this.boundary.getxMin()) / 2;
        int yOffset = this.boundary.getyMin() + (this.boundary.getyMax() - this.boundary.getyMin()) / 2;

        northWest = new QuadTree(this.level + 1, new Boundary(this.boundary.getxMin(), this.boundary.getyMin(), xOffset, yOffset));
        northEast = new QuadTree(this.level + 1, new Boundary(xOffset, this.boundary.getyMin(), this.boundary.getxMax(), yOffset));
        southWest = new QuadTree(this.level + 1, new Boundary(this.boundary.getxMin(), yOffset, xOffset, this.boundary.getyMax()));
        southEast = new QuadTree(this.level + 1, new Boundary(xOffset, yOffset, this.boundary.getxMax(), this.boundary.getyMax()));
    }

    // Insert an entity into the appropriate quadrant
    public void insert(int x, int y, int value) {
        if (!this.boundary.inRange(x, y)) {
            return; // Ignore if the point is outside the boundary
        }

        Entity entity = new Entity(x, y, value);
        if (entities.size() < MAX_CAPACITY) {
            entities.add(entity);
            return;
        }

        if (northWest == null) {
            split(); // Split the node if capacity is exceeded
        }

        // Insert the entity into the appropriate quadrant
        if (northWest.boundary.inRange(x, y)) {
            northWest.insert(x, y, value);
        } else if (northEast.boundary.inRange(x, y)) {
            northEast.insert(x, y, value);
        } else if (southWest.boundary.inRange(x, y)) {
            southWest.insert(x, y, value);
        } else if (southEast.boundary.inRange(x, y)) {
            southEast.insert(x, y, value);
        }
    }

    // Depth-First Search to traverse the QuadTree
    public static void dfs(QuadTree tree) {
        if (tree == null) {
            return;
        }

        System.out.printf("\nLevel = %d [X1=%d Y1=%d] \t[X2=%d Y2=%d] ",
                tree.level, tree.boundary.getxMin(), tree.boundary.getyMin(),
                tree.boundary.getxMax(), tree.boundary.getyMax());

        for (Entity entity : tree.entities) {
            System.out.printf(" \n\t  x=%d y=%d", entity.x, entity.y);
        }
        if (tree.entities.isEmpty()) {
            System.out.printf(" \n\t  Leaf Node.");
        }

        // Recursively traverse the subtrees
        dfs(tree.northWest);
        dfs(tree.northEast);
        dfs(tree.southWest);
        dfs(tree.southEast);
    }

    public static void main(String[] args) {
        QuadTree anySpace = new QuadTree(1, new Boundary(0, 0, 1000, 1000));
        anySpace.insert(100, 100, 1);
        anySpace.insert(500, 500, 1);
        anySpace.insert(600, 600, 1);
        anySpace.insert(700, 600, 1);
        anySpace.insert(800, 600, 1);
        anySpace.insert(900, 600, 1);
        anySpace.insert(510, 610, 1);
        anySpace.insert(520, 620, 1);
        anySpace.insert(530, 630, 1);
        anySpace.insert(540, 640, 1);
        anySpace.insert(550, 650, 1);
        anySpace.insert(555, 655, 1);
        anySpace.insert(560, 660, 1);

        // Perform DFS traversal to print the structure
        QuadTree.dfs(anySpace);
    }
}
