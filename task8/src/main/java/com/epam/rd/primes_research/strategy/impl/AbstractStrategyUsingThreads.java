package com.epam.rd.primes_research.strategy.impl;

import com.epam.rd.primes_research.strategy.IFindPrimesStrategy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public abstract class AbstractStrategyUsingThreads implements IFindPrimesStrategy {
    // create thread-safe collection (one for all)
    protected List<Integer> collection = Collections.synchronizedList(new ArrayList<>());

    @Override
    public Collection<Integer> findAllPrimes(int startDiapason, int endDiapason, int threadAmount) throws InterruptedException {
        Thread[] threads = new Thread[threadAmount];
        for (int i = 0; i < threads.length; i++) {
            Runnable runnable = getRunnable(startDiapason, endDiapason, threadAmount, i);
            (threads[i] = new Thread(runnable)).start();
        }

        // joining all the threads with main
        for (Thread thread : threads) {
            thread.join();
        }

        return collection;
    }

    protected abstract Runnable getRunnable(int startDiapason, int endDiapason, int threadAmount, int threadIndex);
}
