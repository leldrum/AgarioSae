package server;

import worldElements.World;

import java.io.*;

public class SerializationUtils {

    public static String serializeWorldToString(World world) throws IOException {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(world);
            return baos.toString("ISO-8859-1");
        }
    }

    public static World deserializeWorldFromString(String worldString) throws IOException, ClassNotFoundException {
        try (ByteArrayInputStream bais = new ByteArrayInputStream(worldString.getBytes("ISO-8859-1"));
             ObjectInputStream ois = new ObjectInputStream(bais)) {
            return (World) ois.readObject();
        }
    }
}
