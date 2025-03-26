package com.example.client.server;

import com.example.client.server.ClientServer;

import java.io.IOException;

public class testConnexion {
    public static void main(String[] args) throws IOException {
        ClientServer client = new ClientServer("10.42.17.235", 12345);
        //System.out.println(SerializationUtils.serializeWorldToString(World.getInstance()));

    }
}
