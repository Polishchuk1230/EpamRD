package com.epam.rd.primes_research.strategy.impl;

public class UsingThreadsOneCollectionStrategy extends AbstractStrategyUsingThreads {

    @Override
    protected Runnable getRunnable(int startDiapason, int endDiapason, int threadAmount, int threadIndex) {
        return getOneCollectionRunnable(startDiapason, endDiapason, threadAmount, threadIndex);
    }
}
