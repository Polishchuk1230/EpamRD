package com.epam.rd.primes_research.strategy;

import com.epam.rd.primes_research.util.UtilMethods;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class UsingExecutorsSeveralCollectionsStrategy implements IFindPrimesStrategy {

    @Override
    public Collection<Integer> findAllPrimes(int startDiapason, int endDiapason, int threadAmount) {
        System.out.println("UsingExecutorsSeveralCollectionsStrategy is running");
        // create thread-safe collection (one for all)
        List<Integer> collection = Collections.synchronizedList(new ArrayList<>());

        ExecutorService executorService = Executors.newFixedThreadPool(threadAmount);
        for (int i = 0; i < threadAmount; i++) {
            int finalI = i;
            Runnable runnable = () -> {
                // each thread has its own collection
                List<Integer> temp = new ArrayList<>();
                for (int j = startDiapason; j <= endDiapason; j++) {
                    if (j % threadAmount == finalI && UtilMethods.isPrime(j)) {
                        temp.add(j);
                    }
                }
                collection.addAll(temp);
            };
            executorService.execute(runnable);
        }

        // joining all the threads with main
        executorService.shutdown();
        try {
            executorService.awaitTermination(1000, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return collection;
    }
}
