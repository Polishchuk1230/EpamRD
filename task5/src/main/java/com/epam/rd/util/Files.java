package com.epam.rd.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Files {
    /**
     * This method returns all files inside a directory and all subdirectories
     * @param dirPath
     * @return
     */
    public static List<File> findFiles(String dirPath) {
        List<File> resultList = new ArrayList<>();
        File directory = new File(dirPath);
        if (directory.isDirectory()) {
            getFiles(directory, resultList);
        }
        return resultList;
    }

    /**
     * This method recursively collects all files which are actually files into a provided list
     * @param directory
     * @param result
     */
    private static void getFiles(File directory, List<File> result) {
        File[] allFiles = directory.listFiles();
        for (File file : allFiles) {
            if (file.isFile()) {
                result.add(file);
            } else if (file.isDirectory()) {
                getFiles(file, result);
            }
        }
    }
}
