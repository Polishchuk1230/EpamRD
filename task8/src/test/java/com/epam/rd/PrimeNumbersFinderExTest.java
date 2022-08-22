package com.epam.rd;

import com.epam.rd.primes_research.strategy.UsingExecutorsOneCollectionStrategy;
import com.epam.rd.primes_research.strategy.UsingExecutorsSeveralCollectionsStrategy;
import com.epam.rd.primes_research.util.UtilMethods;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

public class PrimeNumbersFinderExTest {

    @Test
    public void findAllPrimesMethodTest() {
        List<Integer> expected = new ArrayList<>(Arrays.asList(19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89));
        Collection<Integer> result = new UsingExecutorsOneCollectionStrategy().findAllPrimes(19, 89, 2);

        Assert.assertTrue(UtilMethods.isContentEqual(expected, result));
    }

    @Test
    public void equalityRandomTest() {
        Random random = new Random();
        int startPoint = random.nextInt(10000);
        int length = random.nextInt(25000);

        Collection<Integer> primes1 = new UsingExecutorsOneCollectionStrategy().findAllPrimes(startPoint, startPoint + length, random.nextInt(5)+1);
        Collection<Integer> primes2 = new UsingExecutorsSeveralCollectionsStrategy().findAllPrimes(startPoint, startPoint + length, random.nextInt(5)+1);

        Assert.assertTrue(UtilMethods.isContentEqual(primes1, primes2));
    }

    @Test
    public void compareTwoMethods() {
        long firstTimePoint = System.currentTimeMillis();
        Collection<Integer> primes1 = new UsingExecutorsOneCollectionStrategy().findAllPrimes(1, 100_000, 6);
        long secondTimePoint = System.currentTimeMillis();
        Collection<Integer> primes2 = new UsingExecutorsSeveralCollectionsStrategy().findAllPrimes(1, 100_000, 6);
        long thirdTimePoint = System.currentTimeMillis();

        Assert.assertTrue(UtilMethods.isContentEqual(primes1, primes2));
        long firstMethodTime = secondTimePoint - firstTimePoint;
        long secondMethodTime = thirdTimePoint - secondTimePoint;

        System.out.printf("First %dms, second: %dms%n", firstMethodTime, secondMethodTime);
    }
}
