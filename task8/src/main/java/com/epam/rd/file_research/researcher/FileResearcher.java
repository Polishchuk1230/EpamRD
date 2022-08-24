package com.epam.rd.file_research.researcher;

import com.epam.rd.file_research.util.FileResearchMethods;

import java.io.Closeable;

public class FileResearcher extends Thread implements Closeable {

    private byte[] bytes;

    private boolean completed = true;

    private int sequenceLength;

    private int[] result;

    /**
     * It returns true if searching is continuing now and false otherwise.
     * @return
     */
    public boolean isCompleted() {
        return completed;
    }

    /**
     * It returns length of the largest repeated sequence of bytes in a file, after the process is completed OR
     * It returns current processed length, if searching is continuing now.
     * @return
     */
    public int getSequenceLength() {
        return sequenceLength;
    }

    /**
     * It returns int[] with two indexes of starting positions the first and second byte sequence duplicate in a byte
     * array of a current file.
     *
     * If there aren't any repeating sequences in the file or the file doesn't exist, it will return null.
     * @return
     */
    public int[] getResult() {
        return result;
    }

    @Override
    public void run() {
        while (isAlive()) {

            // if we have nothing to process, wait
            if (isCompleted()) {
                synchronized (this) {
                    try {
                        wait();
                        continue;
                    } catch (InterruptedException e) {
                        // Ok, this thread was interrupted.
                        break;
                    }
                }
            }

            // start searching
            sequenceLength = bytes.length / 2;
            while (!isCompleted()) {
                result = FileResearchMethods.findRepeatingSequence(bytes, getSequenceLength());
                if (result != null || sequenceLength < 2) {
                    completed = true;
                } else {
                    sequenceLength--;
                }
            }
        }
    }

    /**
     * It looks for a file, downloads the file, wakes the support thread up and makes the thread process the file's bytes
     * @param path
     */
    public void wakeUpAndProcessFile(String path) {
        this.result = null;
        this.bytes = FileResearchMethods.downloadFile(path);

        if (bytes == null) {
            return;
        }

        completed = false;
        synchronized (this) {
            notify();
        }
    }

    @Override
    public void close() {
        interrupt();
    }
}
