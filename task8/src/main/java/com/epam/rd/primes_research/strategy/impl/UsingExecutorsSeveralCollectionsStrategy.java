package com.epam.rd.primes_research.strategy.impl;

public class UsingExecutorsSeveralCollectionsStrategy extends AbstractStrategyUsingExecutors {

    @Override
    protected Runnable getRunnable(int startDiapason, int endDiapason, int threadAmount, int threadIndex) {
        return getSeveralCollectionsRunnable(startDiapason, endDiapason, threadAmount, threadIndex);
    }
}
