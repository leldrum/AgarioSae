package com.example.client.server;

import java.io.*;
import java.net.Socket;

public class NetworkListener implements Runnable {
    private ObjectInputStream input;  // Utilise ObjectInputStream au lieu de BufferedReader

    public NetworkListener(ObjectInputStream input) {
        this.input = input;
    }

    @Override
    public void run() {
        try {
            Object serverMessage;
            while ((serverMessage = input.readObject()) != null) {
                processServerMessage(serverMessage);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void processServerMessage(Object message) {
        if (message instanceof String) {
            // Si le message est une chaîne de caractères
            System.out.println("Mise à jour du serveur: " + message);
        } else {
            // Par défaut, affiche un message pour les autres types d'objets
            System.out.println("Message du serveur reçu: " + message);
        }
    }
}
