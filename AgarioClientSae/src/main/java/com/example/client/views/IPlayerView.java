package com.example.client.views;

import javafx.scene.Camera;

public interface IPlayerView {
    void updateView();
    void animateZoom(double foodValue);
    Camera getCamera();
}
