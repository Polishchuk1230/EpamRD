package com.epam.rd.primes_research.strategy.impl;

import com.epam.rd.primes_research.util.UtilMethods;

public class UsingThreadsOneCollectionStrategy extends AbstractStrategyUsingThreads {

    @Override
    protected Runnable getRunnable(int startDiapason, int endDiapason, int threadAmount, int threadIndex) {
        return () -> {
            int[] threadInterval = UtilMethods.getThreadInterval(startDiapason, endDiapason, threadAmount, threadIndex);
            for (int i = threadInterval[0]; i < threadInterval[1]; i++) {
                if (UtilMethods.isPrime(i)) {
                    collection.add(i);
                }
            }
        };
    }
}
