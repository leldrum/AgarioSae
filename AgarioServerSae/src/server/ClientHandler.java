package server;

import java.net.Socket;

public class ClientHandler implements Runnable {
    private Socket socket;
    private GameServer server;

    public ClientHandler(Socket socket, GameServer server) {
        this.socket = socket;
        this.server = server;
    }

    @Override
    public void run() {

    }

}
