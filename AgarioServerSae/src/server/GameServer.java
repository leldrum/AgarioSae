package server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

public class GameServer {
    private static final int PORT = 12345;
    private ServerSocket serverSocket;
    private final Set<PrintWriter> clientWriters = new HashSet<>();

    public void start() {
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Serveur en écoute sur le port " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Nouvelle connexion : " + clientSocket);

                ClientHandler clientHandler = new ClientHandler(clientSocket, this);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Ajoute un client à la liste des connexions actives
    public synchronized void addClient(PrintWriter writer) {
        clientWriters.add(writer);
    }

    // Supprime un client lorsqu'il se déconnecte
    public synchronized void removeClient(PrintWriter writer) {
        clientWriters.remove(writer);
    }

    // Diffuse un message à tous les clients
    public synchronized void broadcastMessage(String message) {
        for (PrintWriter writer : clientWriters) {
            writer.println(message);
        }
    }

    public static void main(String[] args) {
        GameServer server = new GameServer();
        server.start();
    }
}
