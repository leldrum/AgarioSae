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
        try {
            socket = new Socket(serverIp, port);
            System.out.println("Connexion au serveur " + serverIp + ":" + port);
            input = new ObjectInputStream(socket.getInputStream());
            output = new ObjectOutputStream(socket.getOutputStream());

            // Lire l'objet envoyé par le serveur (cela pourrait être un String ou un autre type)
            Object initData = input.readObject();

            // Vérifier le type de l'objet reçu
            if (initData instanceof String) {
                processInitData((String) initData);
            } else if (initData instanceof World) {
                System.out.println("ca rentre ici");
                World world = (World) initData;
                World.setInstance(world); // Mettre à jour l'instance de World
            } else {
                System.err.println("Données d'initialisation non reconnues.");
            }

            output.writeObject("READY");
            output.flush();

            // Lancer un thread pour écouter les messages réseau
            new Thread(new NetworkListener(input)).start();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
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



    // Méthode pour envoyer la direction du joueur au serveur
    public void sendPlayerDirection(double x, double y) {
        try {
            String message = "MOVE:" + x + "," + y;
            output.writeObject(message);
            output.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
