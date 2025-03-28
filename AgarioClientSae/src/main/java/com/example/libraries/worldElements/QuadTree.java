package com.example.libraries.worldElements;

import java.util.ArrayList;
import java.util.List;

public class QuadTree {
    private static final int MAX_CAPACITY = 4;
    private static final int MAX_LEVELS = 5;

    private final int level;
    private final Boundary boundary;
    private final List<Entity> entities;
    private QuadTree[] subTrees;

    public QuadTree(int level, Boundary boundary) {
        this.level = level;
        this.boundary = boundary;
        this.entities = new ArrayList<>();
        this.subTrees = null;
    }

    public void clear() {
        entities.clear();
        if (subTrees != null) {
            for (QuadTree tree : subTrees) {
                tree.clear();
            }
            subTrees = null;
        }
    }

    public boolean insert(double x, double y, Entity entity) {
        if (!boundary.contains(x, y)) {
            return false;
        }

        if (subTrees == null && entities.size() < MAX_CAPACITY || level >= MAX_LEVELS) {
            entities.add(entity);
            return true;
        }

        if (subTrees == null) {
            split();
        }

        return insertIntoSubTree(x, y, entity);
    }

    private boolean insertIntoSubTree(double x, double y, Entity entity) {
        for (QuadTree tree : subTrees) {
            if (tree.insert(x, y, entity)) {
                return true;
            }
        }
        return false;
    }

    private void split() {
        double xMid = (boundary.xMin + boundary.xMax) / 2.0;
        double yMid = (boundary.yMin + boundary.yMax) / 2.0;

        subTrees = new QuadTree[4];
        subTrees[0] = new QuadTree(level + 1, new Boundary(boundary.xMin, boundary.yMin, xMid, yMid));
        subTrees[1] = new QuadTree(level + 1, new Boundary(xMid, boundary.yMin, boundary.xMax, yMid));
        subTrees[2] = new QuadTree(level + 1, new Boundary(boundary.xMin, yMid, xMid, boundary.yMax));
        subTrees[3] = new QuadTree(level + 1, new Boundary(xMid, yMid, boundary.xMax, boundary.yMax));

        // Redistribuer les entités existantes
        List<Entity> toRedistribute = new ArrayList<>(entities);
        entities.clear();

        for (Entity entity : toRedistribute) {
            double x = entity.entity.getCenterX();
            double y = entity.entity.getCenterY();
            if (!insertIntoSubTree(x, y, entity)) {
                entities.add(entity); // Garder si ne rentre dans aucun sous-arbre
            }
        }
    }

    public List<Entity> queryRange(Boundary range) {
        List<Entity> found = new ArrayList<>();
        queryRange(range, found);
        return found;
    }

    private void queryRange(Boundary range, List<Entity> found) {
        if (!boundary.intersects(range)) {
            return;
        }

        for (Entity entity : entities) {
            if (range.contains(entity.entity.getCenterX(), entity.entity.getCenterY())) {
                found.add(entity);
            }
        }

        if (subTrees != null) {
            for (QuadTree tree : subTrees) {
                tree.queryRange(range, found);
            }
        }
    }

    // Méthode utilitaire pour le débogage
    public void printTree() {
        System.out.printf("Level %d - Boundary [%.1f,%.1f] to [%.1f,%.1f] - Entities: %d%n",
                level, boundary.xMin, boundary.yMin, boundary.xMax, boundary.yMax, entities.size());

        if (subTrees != null) {
            for (QuadTree tree : subTrees) {
                tree.printTree();
            }
        }
    }
}