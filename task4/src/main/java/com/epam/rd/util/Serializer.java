package com.epam.rd.util;

import java.io.*;

public class Serializer {

    public static void serialize(Serializable object, String path) {
        File file = new File(path);

        try (OutputStream outputStream = new FileOutputStream(file);
             ObjectOutput objectOutput = new ObjectOutputStream(outputStream)) {
            objectOutput.writeObject(object);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static  <T extends Serializable> T deserialize(String path) {
        File file = new File(path);

        try (InputStream inputStream = new FileInputStream(file);
             ObjectInput objectInput = new ObjectInputStream(inputStream)) {
            return (T) objectInput.readObject();
        }
        catch (FileNotFoundException e) {
            // leave the result value null
            // do not do anything else
        }
        catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}