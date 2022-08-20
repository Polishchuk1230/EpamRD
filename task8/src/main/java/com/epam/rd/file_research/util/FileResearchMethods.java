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
     * It removes all zeros at a provided array's end.
     * @param source
     * @return
     */
    private static byte[] removeZeroTail(byte[] source) {
        if (source == null) {
            return null;
        }

        int tailCounter = 0;
        while (tailCounter < source.length && source[source.length - tailCounter - 1] == 0) {
            tailCounter++;
        }

        if (tailCounter == 0 || tailCounter == source.length) {
            return source;
        }
        return Arrays.copyOf(source, source.length - tailCounter);
    }

    /**
     * It downloads a file and returns a byte array of the file's bytes.
     * Any IOException causes returning null.
     * @param path
     * @return
     */
    public static byte[] downloadFile(String path) {
        byte[] result = new byte[0];
        byte[] buffer = new byte[1024];
        try (InputStream inputStream = Files.newInputStream(Path.of(path))) {
            while (inputStream.read(buffer) > 0) {
                result = uniteArrays(result, buffer);
            }
        } catch (IOException e) {
            // if there is a problem, return null
            return null;
        }
        return removeZeroTail(result);
    }

    private static byte[] uniteArrays(byte[] arr1, byte[] arr2) {
        byte[] result = new byte[arr1.length + arr2.length];
        System.arraycopy(arr1, 0, result, 0, arr1.length);
        System.arraycopy(arr2, 0, result, arr1.length, arr2.length);
        return result;
    }
}
