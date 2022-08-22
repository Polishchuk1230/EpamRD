package com.epam.rd.primes_research.strategy;

import com.epam.rd.primes_research.util.UtilMethods;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class UsingThreadsSeveralCollectionsStrategy implements IFindPrimesStrategy {

    @Override
    public Collection<Integer> findAllPrimes(int startDiapason, int endDiapason, int threadAmount) {
        System.out.println("UsingThreadsSeveralCollectionsStrategy is running");
        // create thread-safe collection (one for all)
        List<Integer> collection = Collections.synchronizedList(new ArrayList<>());

        Thread[] threads = new Thread[threadAmount];
        for (int i = 0; i < threads.length; i++) {
            int finalI = i;
            Runnable runnable = () -> {
                // each thread has its own collection
                List<Integer> temp = new ArrayList<>();
                for (int j = startDiapason; j <= endDiapason; j++) {
                    if (j % threads.length == finalI && UtilMethods.isPrime(j)) {
                        temp.add(j);
                    }
                }
                collection.addAll(temp);
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
