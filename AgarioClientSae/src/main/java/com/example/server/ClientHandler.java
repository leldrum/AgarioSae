package com.example.server;


import com.example.libraries.utils.SerializationUtils;
import com.example.libraries.worldElements.World;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

public class ClientHandler implements Runnable {
    private Socket socket;
    private GameServer server;
    private PrintWriter out;
    private BufferedReader in;
    private boolean isReady = false;

    public ClientHandler(Socket socket, GameServer server) {
        this.socket = socket;
        this.server = server;
    }

    @Override
    public void run() {
        try{
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            server.addClient(out);

            String worldData = SerializationUtils.serializeWorldToString(World.getInstance());
            server.sendMessage("WORLD:" + worldData, out);
            out.flush();
            System.out.println("Monde envoyé au client : " + worldData);

            System.out.println("Taille : " + World.getInstance().getMapLimitHeight() + ";" + World.getInstance().getMapLimitWidth());
            System.out.println("Group : " + World.getInstance().getRoot());

            String message;
            while ((message = in.readLine()) != null) {
                handleMessage(message);
            }

        }  catch (SocketException e) {
            System.err.println("Connection reset by client: " + socket);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }

    }

    private void handleMessage(String message) {
        if (message.startsWith("READY")) {
            isReady = true;
            System.out.println("Client prêt : " + socket);
        } else if (message.startsWith("MOVE:")) {
            System.out.println("Déplacement reçu : " + message);
        } else if (message.startsWith("CHAT:")) {
            System.out.println("Message chat reçu : " + message.substring(5));
            server.broadcastMessage(message);
        } else {
            System.out.println("Message inconnu : " + message);
        }
    }

    private void closeConnection() {
        try {
            System.out.println("Déconnexion du client : " + socket);
            server.removeClient(this.out);
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}