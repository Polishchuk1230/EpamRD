package com.epam.rd.primes_research.strategy;

import com.epam.rd.primes_research.util.UtilMethods;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class UsingThreadsOneCollectionStrategy implements IFindPrimesStrategy {

    @Override
    public Collection<Integer> findAllPrimes(int startDiapason, int endDiapason, int threadAmount) {
        System.out.println("UsingThreadsOneCollectionStrategy is running");
        // create thread-safe collection (one for all)
        List<Integer> collection = Collections.synchronizedList(new ArrayList<>());

        Thread[] threads = new Thread[threadAmount];
        for (int i = 0; i < threads.length; i++) {
            int finalI = i;
            Runnable runnable = () -> {
                for (int j = startDiapason; j <= endDiapason; j++) {
                    if (j % threads.length == finalI && UtilMethods.isPrime(j)) {
                        collection.add(j);
                    }
                }
            };
            (threads[i] = new Thread(runnable)).start();
        }

        // joining all the threads with main
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        return collection;
    }
}
