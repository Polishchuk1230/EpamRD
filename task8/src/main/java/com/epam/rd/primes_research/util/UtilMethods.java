package com.epam.rd.primes_research.util;

import com.epam.rd.primes_research.entity.Pair;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UtilMethods {
    /**
     * It checks if an int value is prime
     * @param value
     * @return
     */
    public static boolean isPrime(int value) {
        if (value <= 1) {
            return false;
        }
        for (int i = 2; i <= value/2; i++) {
            if (value%i == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * It checks that contents of either c1 and c2 collections are equals.
     * Note: order of elements can be different.
     * @param c1
     * @param c2
     * @return
     * @param <T>
     */
    public static <T> boolean isContentEqual(Collection<T> c1, Collection<T> c2) {
        if (c1 == null || c2 == null) {
            throw new IllegalArgumentException();
        }
        if (c1.size() != c2.size()) {
            return false;
        }

        List<T> temp = new ArrayList<>(c2);
        for (T elem : c1) {
            if (!temp.remove(elem)) {
                return false;
            }
        }

        return temp.isEmpty();
    }

    /**
     * It returns an individual interval of integers for each thread depending on yhe thread's index.
     * @param startD
     * @param endD
     * @param threadAmount
     * @param index
     * @return
     */
    public static Pair<Integer, Integer> getThreadInterval(int startD, int endD, int threadAmount, int index) {
        int length = endD - startD + 1;
        int operationsPerThread = (int) Math.ceil(length/(double) threadAmount);

        int start = startD + index * operationsPerThread;
        int end = Math.min(start + operationsPerThread, endD + 1);
        
        return new Pair<>(start, end);
    }
}
