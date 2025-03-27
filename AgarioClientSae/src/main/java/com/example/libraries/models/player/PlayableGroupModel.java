package com.example.libraries.models.player;

import java.util.ArrayList;
import java.util.Iterator;

public class PlayableGroupModel {
    private ArrayList<PlayerModel> parts = new ArrayList<>();
    private boolean canUpdate;

    public PlayableGroupModel(double x, double y,double initialSize) {
        parts.add(new PlayerModel(x,y,initialSize));
        canUpdate = true;
    }

    public double[] getPosition() {
        double posX = 0, posY = 0;
        for (PlayerModel part : parts) {
            posX += part.getPosition()[0];
            posY += part.getPosition()[1];
        }
        return new double[]{posX / parts.size(), posY / parts.size()};
    }

    public void increaseSize(double foodValue) {
        for (int i = 0; i < parts.size(); i++) {
            parts.get(i).increaseSize(foodValue);
        }
    }

    public double checkCollision() {
        Iterator<PlayerModel> iterator = parts.iterator();
        while (iterator.hasNext()) {
            PlayerModel part = iterator.next();
            double result = 0;//part.checkCollision();
            if (result != 0 && result != 1) {
                increaseSize(result);
            }
            if (result == 1) {
                iterator.remove();
                if (parts.isEmpty()) return 1; // Game Over
            }
        }
        return 0;
    }

    /*public double totalRadius() {
        return parts.stream().mapToDouble(PlayerModel::totalRadius).sum();
    }

    public void divide() {
        ArrayList<PlayerModel> newParts = new ArrayList<>();
        for (PlayerModel part : parts) {
            if (part.canDivide()) {
                newParts.add(part.divide());
            }
        }
        parts.addAll(newParts);
        canUpdate = false;
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                canUpdate = true;
            }
        }, 10000);
    }

    public void union() {
        if (canUpdate && parts.size() > 1) {
            parts.get(0).doubleSize();
            parts.subList(1, parts.size()).clear();
        }
    }*/

    public boolean isGameOver() {
        return parts.isEmpty();
    }

    public ArrayList<PlayerModel> getParts() {
        return parts;
    }
}
