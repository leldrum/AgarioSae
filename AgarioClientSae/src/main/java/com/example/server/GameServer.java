package com.example.server;
import com.example.libraries.worldElements.World;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class GameServer {
    private ServerSocket serverSocket;
    private final int PORT = 1234;
    private static final List<ClientHandler> clients = new ArrayList<>();

    public void start() {
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Serveur démarré sur le port " + PORT);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connecté : " + clientSocket.getInetAddress());
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                clients.add(clientHandler);
                clientHandler.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void broadcastWorld() {
        for (ClientHandler client : clients) {
            try {
                client.output.reset();
                client.output.writeObject(World.getInstance());
                client.output.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new GameServer().start();
    }
}