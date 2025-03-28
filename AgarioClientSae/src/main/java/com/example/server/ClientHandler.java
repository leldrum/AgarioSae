package com.example.server;
import com.example.libraries.worldElements.World;
import java.io.*;
import java.net.Socket;
class ClientHandler extends Thread {
    private Socket clientSocket;
    private ObjectInputStream input;
    ObjectOutputStream output;
    private static final World world = World.getInstance();

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
            output.writeObject(world);
            output.flush();
            while (true) {
                String command = (String) input.readObject();
                if (command.startsWith("MOVE:")) {
                    String[] parts = command.split(":" )[1].split(",");
                    double x = Double.parseDouble(parts[0]);
                    double y = Double.parseDouble(parts[1]);
                    System.out.println("Déplacement reçu: X=" + x + ", Y=" + y);
                }
                GameServer.broadcastWorld();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}