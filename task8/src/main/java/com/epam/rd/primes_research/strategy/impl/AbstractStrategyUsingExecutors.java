package com.epam.rd.primes_research.strategy.impl;

import com.epam.rd.primes_research.strategy.IFindPrimesStrategy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public abstract class AbstractStrategyUsingExecutors implements IFindPrimesStrategy {
    // create thread-safe collection (one for all)
    protected List<Integer> collection = Collections.synchronizedList(new ArrayList<>());

    @Override
    public Collection<Integer> findAllPrimes(int startDiapason, int endDiapason, int threadAmount) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(threadAmount);
        for (int i = 0; i < threadAmount; i++) {
            Runnable runnable = getRunnable(startDiapason, endDiapason, threadAmount, i);
            executorService.execute(runnable);
        }

        // joining all the threads with main
        executorService.shutdown();
        executorService.awaitTermination(1000, TimeUnit.SECONDS);

        return collection;
    }

    protected abstract Runnable getRunnable(int startDiapason, int endDiapason, int threadAmount, int threadIndex);
}
