package com.example.client.server;

import com.example.libraries.utils.SerializationUtils;
import com.example.libraries.worldElements.World;
import java.io.*;
import java.net.Socket;

public class ClientServer {
    private Socket socket;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private int playerId;

    public ClientServer(String serverIp, int port) {
        boolean connected = false;
        while (!connected) {
            try {
                socket = new Socket(serverIp, port);
                System.out.println("Connexion au serveur " + serverIp + ":" + port);
                input = new ObjectInputStream(socket.getInputStream());
                output = new ObjectOutputStream(socket.getOutputStream());
                Object initData = input.readObject();
                if (initData instanceof String) {
                    processInitData((String) initData);
                } else if (initData instanceof World) {
                    System.out.println("Données de monde reçues");
                    World world = (World) initData;
                    World.setInstance(world);
                } else {
                    System.err.println("Données d'initialisation non reconnues.");
                }

                // Confirmer que le client est prêt
                output.writeObject("READY");
                output.flush();

                // Démarrer un thread pour écouter les messages réseau
                new Thread(new NetworkListener(input)).start();

                connected = true; // Connexion réussie
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Échec de la connexion. Tentative de reconnexion...");
                try {
                    Thread.sleep(2000); // Attendre 2 secondes avant de réessayer
                } catch (InterruptedException ie) {
                    ie.printStackTrace();
                }
            }
        }
    }

    private void processInitData(Object data) {
        try {
            if (data instanceof String) {
                System.out.println("Données d'initialisation reçues (String) : " + data);
                String initData = (String) data;
                String[] parts = initData.split(",");
                for (String part : parts) {
                    if (part.startsWith("ID:")) {
                        playerId = Integer.parseInt(part.split(":")[1]);
                        System.out.println("ID reçu : " + playerId);
                    }
                    if (part.startsWith("WORLD:")) {
                        String worldData = part.split(":")[1];
                        System.out.println("Données du monde reçues : " + worldData);
                        World world = SerializationUtils.deserializeWorldFromString(worldData);
                        System.out.println("Monde désérialisé : " + world);
                        World.setInstance(world);
                    }
                }
            } else if (data instanceof World) {
                System.out.println("Données d'initialisation reçues (World) : " + data);
                World world = (World) data;
                World.setInstance(world);
            } else {
                System.err.println("Type de données non reconnu.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Client connecté avec ID: " + playerId);
    }


    // Méthode pour fermer la connexion proprement
    public void closeConnection() {
        try {
            input.close();
            output.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
