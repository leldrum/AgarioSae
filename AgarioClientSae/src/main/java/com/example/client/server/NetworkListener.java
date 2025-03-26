package com.example.client.server;

import java.io.BufferedReader;
import java.io.IOException;

public class NetworkListener implements Runnable {
    private BufferedReader input;

    public NetworkListener(BufferedReader input) {
        this.input = input;
    }

    @Override
    public void run() {
        try {
            String serverMessage;
            while ((serverMessage = input.readLine()) != null) {
                processServerMessage(serverMessage);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void processServerMessage(String message) {
        System.out.println("Mise à jour du serveur: " + message);
        // Ici, on pourrait mettre à jour les entités du jeu (joueurs, pastilles, etc.)
        //String[] parts = data.split(",");
    }
}

