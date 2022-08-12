import com.epam.rd.dto.FilterRequest;
import com.epam.rd.filters.*;
import com.epam.rd.util.Files;

import java.io.File;
import java.util.*;
import java.util.function.Consumer;

public class Main {
    public static void main(String[] args) {
        List<File> files;
        FilterRequest filterRequest = new FilterRequest();

        // parameters for the FilterRequest instance
        Map<String, Consumer<String>> parameters = new HashMap<>();
        parameters.put("Type a name. Example: filename.txt", filterRequest::setFileName);
        parameters.put("Type a type. Example: txt", filterRequest::setFileExtension);
        parameters.put("Type size diapason in bytes. Example: 10000 - 50000", filterRequest::setFileSizeDiapason);
        parameters.put("Type change period. Example: 01.01.2022 00:00:00 - 01.05.2022 23:59:59", filterRequest::setFileChangeDateDiapason);

        // collect information from a user
        try (Scanner scanner = new Scanner(System.in)) {
            // get a directory path
            System.out.println("Type a path to a directory to search in.");
            files = Files.findFiles(scanner.nextLine());

            // collect filterRequest
            for (Map.Entry<String, Consumer<String>> entry : parameters.entrySet()) {
                System.out.println(entry.getKey());

                String answer = scanner.nextLine();
                if (answer.isEmpty()) {
                    continue;
                } else if (answer.equals("stop")) {
                    break;
                }
                entry.getValue().accept(answer);
            }
        }

        // getPredicate files with our chain-filters
        AbstractFileFilter filterChain = new FileFilterByName(new FileFilterByExtension(
                new FileFilterBySizeDiapason(new FileFilterByChangePeriod(null))));
        filterChain.filter(files, filterRequest);

        // do something with the suitable files
        files.stream()
                .map(File::getName)
                .forEach(System.out::println);
    }
}
