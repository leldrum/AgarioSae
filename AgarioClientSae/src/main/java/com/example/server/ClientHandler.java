package com.example.server;

import com.example.libraries.worldElements.World;

import java.io.*;
import java.net.Socket;

class ClientHandler extends Thread {
    private Socket clientSocket;
    private ObjectInputStream input;
    private ObjectOutputStream output;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
        try {
            output = new ObjectOutputStream(clientSocket.getOutputStream());
            input = new ObjectInputStream(clientSocket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            // Récupère l'instance du monde
            World world = World.getInstance();

            // Sérialise l'objet World et l'envoie au client
            output.writeObject(world);
            output.flush();
            System.out.println("Monde envoyé au client");

            // Envoie de données supplémentaires ou gestion des commandes ici si nécessaire

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
