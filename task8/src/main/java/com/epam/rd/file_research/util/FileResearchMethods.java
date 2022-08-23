package com.epam.rd.file_research.util;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class FileResearchMethods {

    /**
     * It looks for start indexes of repeating byte sequence of the concrete length.
     * It returns an integer array of the first and second start indexes or null, if there's no a repeating sequence.
     * @param bytes
     * @param length
     * @return
     */
    public static int[] findRepeatingSequence(byte[] bytes, int length) {
        for (int a = 0; a + length * 2 <= bytes.length; a++) {
            for (int b = a + length; b + length <= bytes.length; b++) {
                if (Arrays.mismatch(bytes, a, a + length, bytes, b, b + length) == -1) {
                    return new int[] {a, b};
                }
            }
        }
        return null;
    }

    /**
     * It downloads a file and returns a byte array of the file's bytes.
     * Any IOException causes returning null.
     * @param path
     * @return
     */
    public static byte[] downloadFile(String path) {
        try (InputStream inputStream = Files.newInputStream(Path.of(path))) {
            return inputStream.readAllBytes();
        } catch (IOException e) {
            // if there is a problem, return null
            return null;
        }
    }
}
