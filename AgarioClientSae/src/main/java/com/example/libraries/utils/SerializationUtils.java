package com.example.libraries.utils;

import com.example.libraries.models.worldElements.WorldModel;

import java.io.*;
import java.util.Base64;

public class SerializationUtils {

    public static String serializeWorldToString(WorldModel world) throws IOException {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(world);
            return Base64.getEncoder().encodeToString(baos.toByteArray());
        }
    }

    public static WorldModel deserializeWorldFromString(String worldString) throws IOException, ClassNotFoundException {
        byte[] data = Base64.getDecoder().decode(worldString);
        try (ByteArrayInputStream bais = new ByteArrayInputStream(data);
             ObjectInputStream ois = new ObjectInputStream(bais)) {
            return (WorldModel) ois.readObject();
        }
    }
}