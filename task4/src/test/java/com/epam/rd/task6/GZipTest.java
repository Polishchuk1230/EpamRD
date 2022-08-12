package com.epam.rd.task6;

import org.junit.Test;

import java.io.*;
import java.util.zip.GZIPOutputStream;

public class GZipTest {

    @Test
    public void gzipTest() {

        byte[] buffer = new byte[1024];
        try (FileOutputStream fileOutputStream = new FileOutputStream("../task4/src/main/resources/test.gz");
             GZIPOutputStream gzipOutputStream = new GZIPOutputStream(fileOutputStream);
             FileInputStream fileInputStream = new FileInputStream("../task4/src/main/resources/product_storage.ser")) {

            int totalSize;
            while ((totalSize = fileInputStream.read(buffer)) > 0) {
                gzipOutputStream.write(buffer, 0, totalSize);
            }

            System.out.println("The file was successfully written.");
        } catch (FileNotFoundException e) {
            System.out.println("If you see this message, make the application to save some data into ../task4/src/main/resources/test.gz");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
