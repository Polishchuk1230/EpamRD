package com.epam.rd.primes_research.strategy;

import java.util.Collection;

public interface IFindPrimesStrategy {
    Collection<Integer> findAllPrimes(int startDiapason, int endDiapason, int threadAmount) throws InterruptedException;
}
