package com.epam.rd.primes_research.strategy.impl;

import java.util.Collection;

public abstract class AbstractStrategyUsingThreads extends AbstractStrategy {

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
