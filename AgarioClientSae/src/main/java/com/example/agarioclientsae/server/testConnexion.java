package com.example.agarioclientsae.server;

import com.example.agarioclientsae.worldElements.World;

import java.io.IOException;

public class testConnexion {
    public static void main(String[] args) throws IOException {
        ClientServer client = new ClientServer("10.42.17.235", 12345);
        System.out.println(SerializationUtils.serializeWorldToString(World.getInstance()));
    }
}
