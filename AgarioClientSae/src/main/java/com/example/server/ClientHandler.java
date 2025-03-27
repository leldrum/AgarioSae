package com.example.server;


import com.example.libraries.utils.SerializationUtils;
import com.example.libraries.models.worldElements.WorldModel;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

public class ClientHandler implements Runnable {
    private Socket socket;
    private GameServer server;
    private PrintWriter out;
    private BufferedReader in;

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

            server.sendMessage("WORLD:"+ SerializationUtils.serializeWorldToString(WorldModel.getInstance()), out);
            System.out.println("Taille : " + WorldModel.getInstance().getMapHeight() + ";" + WorldModel.getInstance().getMapWidth());
        }  catch (SocketException e) {
            System.err.println("Connection reset by client: " + socket);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}