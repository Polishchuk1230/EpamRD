package com.epam.rd.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Scanner;

/**
 * (Task 5.1) Text file wrapper to read the file's content by lines.
 */
public class FileReader implements Iterable<String> {
    private final File file;

    public FileReader(String path) {
        this.file = new File(path);
    }

    @Override
    public Iterator<String> iterator() {
        return new FileIterator();
    }

    private class FileIterator implements Iterator<String> {
        private final Scanner sc;

        private FileIterator() {
            try {
                sc = new Scanner(file);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public boolean hasNext() {
            boolean result = sc.hasNextLine();
            if (!result) {
                sc.close();
            }
            return result;
        }

        @Override
        public String next() {
            return sc.nextLine();
        }
    }
}
