package com.epam.rd.primes_research.strategy.impl;

import com.epam.rd.primes_research.util.UtilMethods;

import java.util.ArrayList;
import java.util.List;

public class UsingThreadsSeveralCollectionsStrategy extends AbstractStrategyUsingThreads {

    @Override
    protected Runnable getRunnable(int startDiapason, int endDiapason, int threadAmount, int threadIndex) {
        return () -> {
            int[] threadInterval = UtilMethods.getThreadInterval(startDiapason, endDiapason, threadAmount, threadIndex);
            // each thread has its own collection
            List<Integer> temp = new ArrayList<>();
            for (int i = threadInterval[0]; i < threadInterval[1]; i++) {
                if (UtilMethods.isPrime(i)) {
                    temp.add(i);
                }
            }
            collection.addAll(temp);
        };
    }
}
