package com.epam.rd.container;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class SetListTest {

    @Test
    public void constructorTest() {
        SetList<String> list = new SetList<>(Arrays.asList("one", "two"));
        String[] expected = { "one", "two" };
        Assert.assertArrayEquals(list.toArray(), expected);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorWithIllegalParameter() {
        SetList<String> list = new SetList<>(Arrays.asList("one", "one"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void addWhenElementIsIllegal() {
        SetList<String> list = new SetList<>();
        list.add("one");
        list.add("one");
    }

    @Test(expected = IllegalArgumentException.class)
    public void setWhenElementIsIllegal() {
        SetList<String> list = new SetList<>(Arrays.asList("one", "two"));
        list.set(0, "two");
    }

    @Test
    public void addAllTest() {
        SetList<String> list = new SetList<>();
        Assert.assertTrue(
                list.addAll(
                        Arrays.asList("one", "two")));

        String[] expected = { "one", "two" };
        Assert.assertArrayEquals(list.toArray(), expected);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addAllWhenElementIsIllegal() {
        SetList<String> list = new SetList<>();
        list.addAll(Arrays.asList("one", "one"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void replaceAllWhenOperatorIsIllegal() {
        SetList<String> list = new SetList<>();
        list.addAll(Arrays.asList("one", "two"));
        list.replaceAll(str -> "one");
    }
}
