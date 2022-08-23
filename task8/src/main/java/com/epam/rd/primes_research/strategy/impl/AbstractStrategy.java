package com.epam.rd.primes_research.strategy.impl;

import com.epam.rd.primes_research.entity.Pair;
import com.epam.rd.primes_research.strategy.IFindPrimesStrategy;
import com.epam.rd.primes_research.util.UtilMethods;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class AbstractStrategy implements IFindPrimesStrategy {
    // create thread-safe collection (one for all)
    protected List<Integer> collection = Collections.synchronizedList(new ArrayList<>());

    protected Runnable getOneCollectionRunnable(int startDiapason, int endDiapason, int threadAmount, int threadIndex) {
        return () -> {
            Pair<Integer, Integer> threadInterval = UtilMethods.getThreadInterval(startDiapason, endDiapason, threadAmount, threadIndex);
            for (int i = threadInterval.getLeft(); i < threadInterval.getRight(); i++) {
                if (UtilMethods.isPrime(i)) {
                    collection.add(i);
                }
            }
        };
    }

    protected Runnable getSeveralCollectionsRunnable(int startDiapason, int endDiapason, int threadAmount, int threadIndex) {
        return () -> {
            Pair<Integer, Integer> threadInterval = UtilMethods.getThreadInterval(startDiapason, endDiapason, threadAmount, threadIndex);
            // each thread has its own collection
            List<Integer> temp = new ArrayList<>();
            for (int i = threadInterval.getLeft(); i < threadInterval.getRight(); i++) {
                if (UtilMethods.isPrime(i)) {
                    temp.add(i);
                }
            }
            collection.addAll(temp);
        };
    }
}
