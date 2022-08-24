package com.epam.rd;

import com.epam.rd.primes_research.util.UtilMethods;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UtilMethodsTest {

    @Test
    public void isPrimeTest() {
        // not prime numbers
        Assert.assertFalse(UtilMethods.isPrime(-2));
        Assert.assertFalse(UtilMethods.isPrime(0));
        Assert.assertFalse(UtilMethods.isPrime(1));
        Assert.assertFalse(UtilMethods.isPrime(4));

        // prime numbers
        Assert.assertTrue(UtilMethods.isPrime(2));
        Assert.assertTrue(UtilMethods.isPrime(7));
        Assert.assertTrue(UtilMethods.isPrime(11));
        Assert.assertTrue(UtilMethods.isPrime(10_007));
    }

    @Test
    public void isContentEqualFirstTest() {
        List<Integer> example1 = new ArrayList<>(Arrays.asList(1, 2, 2, 3));
        List<Integer> example2 = new ArrayList<>(Arrays.asList(1, 2, 3, 3));
        Assert.assertFalse(UtilMethods.isContentEqual(example1, example2));
    }

    @Test
    public void isContentEqualSecondTest() {
        List<Integer> example1 = new ArrayList<>(Arrays.asList(4, 2, 1, 3));
        List<Integer> example2 = new ArrayList<>(Arrays.asList(1, 2, 3, 4));
        Assert.assertTrue(UtilMethods.isContentEqual(example1, example2));
    }
}
