import com.epam.rd.file_research.FileResearcher;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in);
             FileResearcher fileResearcher = new FileResearcher()) {
            fileResearcher.start();

            String command;
            while (!(command = sc.nextLine()).equals("exit")) {
                fileResearcher.wakeUpAndProcessFile(command);

                // once per 300ms display the current condition of searching
                while (!fileResearcher.isCompleted()) {
                    slowdown(300);
                    System.out.println("Looking for a sequence with length = " + fileResearcher.getSequenceLength());
                }

                // after searching is over, display a result
                if (fileResearcher.getResult() != null) {
                    int[] indexes = fileResearcher.getResult();
                    System.out.printf("A sequence was found: length = %d(bytes), first index = %d, second index = %d.\n",
                            fileResearcher.getSequenceLength(), indexes[0], indexes[1]);
                } else {
                    System.out.println("The file is empty or is consisted of unique bytes.\n");
                }
            }
        }
    }

    private static void slowdown(int mills) {
        try {
            Thread.sleep(mills);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
