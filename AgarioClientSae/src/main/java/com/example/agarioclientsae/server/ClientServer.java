package com.example.agarioclientsae.server;

import com.example.agarioclientsae.app.HelloApplication;

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

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void processInitData(String data) {
        // Exemple : "ID:1,WORLD_SIZE:1000,FOOD:100,PLAYERS:5"
        System.out.println(data);
        String[] parts = data.split(",");
        for (String part : parts) {
            if (part.startsWith("ID:")) {
                playerId = Integer.parseInt(part.split(":")[1]);
            }
            if (part.startsWith("WORLD_SIZE:")) {
                playerId = Integer.parseInt(part.split(":")[1]);
            }
            if (part.startsWith("FOOD:")) {
                playerId = Integer.parseInt(part.split(":")[1]);
            }
            if (part.startsWith("PLAYERS:")) {
                playerId = Integer.parseInt(part.split(":")[1]);
            }
        }
        System.out.println("Connecté avec ID: " + playerId);
    }

    public void sendPlayerDirection() {
        double[] data = HelloApplication.getMousePosition();
        output.println("MOVE:" + data[0] + "," + data[1]);
    }
}
