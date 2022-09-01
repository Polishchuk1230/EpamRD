package com.epam.rd.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.FileNotFoundException;

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