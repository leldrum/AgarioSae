package server;

import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private Socket socket;
    private GameServer server;

    private PrintWriter out;

    public ClientHandler(Socket socket, GameServer server) {
        this.socket = socket;
        this.server = server;

        try {
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        server.addClient(out);
        server.broadcastMessage("ID:69");
    }

}
