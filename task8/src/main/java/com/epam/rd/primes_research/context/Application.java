package com.epam.rd.primes_research.context;

import com.epam.rd.primes_research.strategy.*;

import java.util.Collection;
import java.util.Optional;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Application {
    private static final int MAX_THREADS = 10;
    private Pattern pattern = Pattern.compile("(\\d+)-(\\d+)(?:, threads=(\\d+))?(?:, strategy=(\\d+))?");

    private IFindPrimesStrategy[] strategies;

    {
        strategies = new IFindPrimesStrategy[] {
                new UsingThreadsOneCollectionStrategy(),
                new UsingThreadsSeveralCollectionsStrategy(),
                new UsingExecutorsOneCollectionStrategy(),
                new UsingExecutorsSeveralCollectionsStrategy()
        };
    }

    public void start() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Type in a diapason to find all its prime numbers, " +
                    "(optional) amount of threads to process this request and (optional) an index of a strategy.\n" +
                    "Example: 1-10000, threads=2, strategy=3");

            while (scanner.hasNextLine()) {
                String command = scanner.nextLine();
                Matcher matcher = pattern.matcher(command);

                if (command.equals("exit")) {
                    break;
                } else if (!matcher.find()) {
                    continue;
                }

                int startDiapason = Integer.parseInt(matcher.group(1));
                int endDiapason = Integer.parseInt(matcher.group(2));
                int threads = Optional.ofNullable(matcher.group(3))
                        .map(Integer::parseInt)
                        .filter(th -> th <= MAX_THREADS)
                        .orElse(1);
                int strategyIndex = Optional.ofNullable(matcher.group(4))
                        .map(Integer::parseInt)
                        .filter(i -> i < strategies.length)
                        .orElse(0);

                Collection<Integer> primes = strategies[strategyIndex].findAllPrimes(startDiapason, endDiapason, threads);
                System.out.println(primes);
            }
        }
    }

}
