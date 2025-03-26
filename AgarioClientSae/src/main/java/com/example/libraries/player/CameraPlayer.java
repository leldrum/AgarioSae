package com.example.libraries.player;

import javafx.scene.ParallelCamera;

public class CameraPlayer extends ParallelCamera {
    private double x;
    private double y;
    private double zoom;


    public CameraPlayer(){
    }

    public double getX(){
        return x;
    }

    public double getY(){
        return y;
    }

    public double getZoom(){
        return zoom;
    }

    public void setX(double x){
        this.x = x;
    }

    public void setY(double y){
        this.y = y;
    }

    public void setZoom(double zoom){
        this.zoom = zoom;
    }

    public void addX(double x){
        this.x += x;
    }

    public void addY(double y){
        this.y += y;
    }

    public void addZoom(double zoom){
        this.zoom += zoom;
    }
}
