package com.example.client.server;

import com.example.client.app.HelloApplication;
import com.example.client.app.MenuStart;
import com.example.libraries.utils.SerializationUtils;
import com.example.libraries.worldElements.World;

import java.io.*;
import java.net.Socket;

public class ClientServer {
    private Socket socket;
    private BufferedReader input;
    private PrintWriter output;
    private int playerId;

    public ClientServer(String serverIp, int port) {
        try {
            socket = new Socket(serverIp, port);
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream(), true);

            String initData = input.readLine();
            processInitData(initData);

            output.println("READY");

            new Thread(new NetworkListener(input)).start();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void processInitData(String data) throws IOException, ClassNotFoundException {
        // Exemple : "ID:1,WORLD_SIZE:1000,FOOD:100,PLAYERS:5"
        System.out.println(data);
        String[] parts = data.split(",");
        for (String part : parts) {
            if (part.startsWith("ID:")) {
                playerId = Integer.parseInt(part.split(":")[1]);
            }
            if (part.startsWith("WORLD:")) {
                SerializationUtils.deserializeWorldFromString(part.split(":")[1]);
                World.setInstance(SerializationUtils.deserializeWorldFromString(part.split(":")[1]));
                System.out.println("Taille du mondeeeeeeee: " + World.getInstance().getMapLimitWidth() + "x" + World.getInstance().getMapLimitHeight());
            }
            /*if (part.startsWith("Taille:")) {
                System.out.println("Taille du monde: " + part.split(":")[1]);

            }*/

        }
        System.out.println("Connecté avec ID: " + playerId);

        javafx.application.Platform.runLater(() -> {
           HelloApplication.startGameClient(MenuStart.getStage()); // Utilisez une méthode spécifique pour démarrer le jeu
        });
    }

    public void sendPlayerDirection() {
        double[] data = HelloApplication.getMousePosition();
        output.println("MOVE:" + data[0] + "," + data[1]);
    }
}
