package com.epam.rd.primes_research.strategy.impl;

public class UsingThreadsSeveralCollectionsStrategy extends AbstractStrategyUsingThreads {

    @Override
    protected Runnable getRunnable(int startDiapason, int endDiapason, int threadAmount, int threadIndex) {
        return getSeveralCollectionsRunnable(startDiapason, endDiapason, threadAmount, threadIndex);
    }
}
