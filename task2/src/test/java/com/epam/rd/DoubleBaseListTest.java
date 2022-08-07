package com.epam.rd;

import com.epam.rd.container.DoubleBaseList;
import com.epam.rd.pojo.Product;
import com.epam.rd.pojo.GamingChair;
import com.epam.rd.pojo.RockingChair;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;

public class DoubleBaseListTest {

    @Test
    public void iteratorTest() {
        Product chair1 = new GamingChair("ASUS Super model", 100000, 120, true, true);
        Product chair2 = new GamingChair("HomeMade not super model", 400, 75, true, false);
        // create a list with immutable part
        DoubleBaseList<Product> list = new DoubleBaseList<>(Arrays.asList(chair1));
        // add a mutable part
        list.add(chair2);

        Iterator<Product> iterator = list.iterator();
        Assert.assertTrue(iterator.hasNext());
        Assert.assertEquals(chair1, iterator.next());
        Assert.assertTrue(iterator.hasNext());
        Assert.assertEquals(chair2, iterator.next());
    }

    @Test(expected = ConcurrentModificationException.class)
    public void iteratorWithConcurrentModificationTest() {
        Product chair1 = new GamingChair("ASUS Super model", 100000, 120, true, true);
        Product chair2 = new GamingChair("HomeMade not super model", 400, 75, true, false);
        Product chair3 = new RockingChair("Uncle Sam model", 399.99, 80, 35);
        // create a list with immutable part
        DoubleBaseList<Product> list = new DoubleBaseList<>(Arrays.asList(chair1));
        // add a mutable part
        list.add(chair2);

        Iterator<Product> iterator = list.iterator();
        Assert.assertTrue(iterator.hasNext());
        Assert.assertEquals(chair1, iterator.next());

        list.add(1, chair3);

        if (iterator.hasNext()) {
            iterator.next();
        }
    }

    @Test
    public void addTest() {
        Product chair1 = new GamingChair("ASUS Super model", 100000, 120, true, true);
        Product chair2 = new GamingChair("HomeMade not super model", 400, 75, true, false);
        // create a list with immutable part
        DoubleBaseList<Product> list = new DoubleBaseList<>(Arrays.asList(chair1));
        // add a mutable part
        Assert.assertTrue(
                list.add(chair2));
        Product[] expected = {chair1, chair2};
        Assert.assertArrayEquals(expected, list.toArray());
    }

    @Test(expected = NullPointerException.class)
    public void addWhenElementIsNull() {
        DoubleBaseList<Product> list = new DoubleBaseList<>(null);
        list.add(null);
    }

    @Test
    public void addByIndexTest() {
        Product chair1 = new GamingChair("ASUS Super model", 100000, 120, true, true);
        Product chair2 = new GamingChair("HomeMade not super model", 400, 75, true, false);
        Product chair3 = new RockingChair("Uncle Sam model", 399.99, 80, 35);
        DoubleBaseList<Product> list = new DoubleBaseList<>(null);
        // add a mutable part
        list.add(0, chair1);
        list.add(1, chair2);
        list.add(2, chair3);
        list.add(1, chair3);

        Product[] expected = {chair1, chair3, chair2, chair3};
        Assert.assertArrayEquals(expected, list.toArray());
    }

    @Test(expected = IllegalArgumentException.class)
    public void addWhenIndexOnImmutablePart() {
        Product chair1 = new GamingChair("ASUS Super model", 100000, 120, true, true);
        Product chair2 = new GamingChair("HomeMade not super model", 400, 75, true, false);
        DoubleBaseList<Product> list = new DoubleBaseList<>(Arrays.asList(chair1));
        // try to change the immutable part
        list.add(0, chair2);
    }

    @Test(expected = NullPointerException.class)
    public void addByIndexWhenElementIsNull() {
        DoubleBaseList<Product> list = new DoubleBaseList<>(null);
        list.add(0, null);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void addWhenIndexIsWrong() {
        Product chair1 = new GamingChair("ASUS Super model", 100000, 120, true, true);
        DoubleBaseList<Product> list = new DoubleBaseList<>(null);
        list.add(1, chair1);
    }

    @Test
    public void addAllTest() {
        Product chair1 = new GamingChair("ASUS Super model", 100000, 120, true, true);
        Product chair2 = new GamingChair("HomeMade not super model", 400, 75, true, false);
        Product chair3 = new RockingChair("Uncle Sam model", 399.99, 80, 35);
        // add an immutable part
        DoubleBaseList<Product> list = new DoubleBaseList<>(Arrays.asList(chair1, chair2));
        // add a mutable part
        list.addAll(
                Arrays.asList(chair3, chair1));

        Product[] expected = {chair1, chair2, chair3, chair1};
        Assert.assertArrayEquals(expected, list.toArray());
    }

    @Test
    public void addAllByIndexTest() {
        Product chair1 = new GamingChair("ASUS Super model", 100000, 120, true, true);
        Product chair2 = new GamingChair("HomeMade not super model", 400, 75, true, false);
        Product chair3 = new RockingChair("Uncle Sam model", 399.99, 80, 35);
        // add an immutable part
        DoubleBaseList<Product> list = new DoubleBaseList<>(Arrays.asList(chair1, chair2));
        // add a mutable part
        list.addAll(2,
                Arrays.asList(chair3, chair1));

        Product[] expected = {chair1, chair2, chair3, chair1};
        Assert.assertArrayEquals(expected, list.toArray());
    }

    @Test(expected = IllegalArgumentException.class)
    public void addAllWhenIndexOnImmutablePart() {
        Product chair1 = new GamingChair("ASUS Super model", 100000, 120, true, true);
        Product chair2 = new GamingChair("HomeMade not super model", 400, 75, true, false);
        Product chair3 = new RockingChair("Uncle Sam model", 399.99, 80, 35);
        // add an immutable part
        DoubleBaseList<Product> list = new DoubleBaseList<>(Arrays.asList(chair1, chair2));
        // add a mutable part touching immutable side
        list.addAll(1,
                Arrays.asList(chair3, chair1));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void addAllWhenIndexOutOfBounds() {
        Product chair1 = new GamingChair("ASUS Super model", 100000, 120, true, true);
        Product chair2 = new GamingChair("HomeMade not super model", 400, 75, true, false);
        Product chair3 = new RockingChair("Uncle Sam model", 399.99, 80, 35);
        // add an immutable part
        DoubleBaseList<Product> list = new DoubleBaseList<>(Arrays.asList(chair1, chair2));
        // add a mutable part by wrong index
        list.addAll(3,
                Arrays.asList(chair3, chair1));
    }

    @Test
    public void removeTest() {
        Product chair1 = new GamingChair("ASUS Super model", 100000, 120, true, true);
        Product chair2 = new GamingChair("HomeMade not super model", 400, 75, true, false);
        Product chair3 = new RockingChair("Uncle Sam model", 399.99, 80, 35);
        // add an immutable part
        DoubleBaseList<Product> list = new DoubleBaseList<>(Arrays.asList(chair1, chair2));
        // add a mutable part
        list.add(chair3);
        // remove from the mutable part
        Assert.assertTrue(
                list.remove(chair3));

        Product[] expected = {chair1, chair2};
        Assert.assertArrayEquals(expected, list.toArray());
    }

    @Test(expected = IllegalArgumentException.class)
    public void removeWhenObjectInImmutablePartTest() {
        Product chair1 = new GamingChair("ASUS Super model", 100000, 120, true, true);
        Product chair2 = new GamingChair("HomeMade not super model", 400, 75, true, false);
        // add an immutable part
        DoubleBaseList<Product> list = new DoubleBaseList<>(Arrays.asList(chair1, chair2));
        // remove from the immutable part
        list.remove(chair2);
    }

    @Test
    public void removeByIndexTest() {
        Product chair1 = new GamingChair("ASUS Super model", 100000, 120, true, true);
        Product chair2 = new GamingChair("HomeMade not super model", 400, 75, true, false);
        Product chair3 = new RockingChair("Uncle Sam model", 399.99, 80, 35);
        // add an immutable part
        DoubleBaseList<Product> list = new DoubleBaseList<>(Arrays.asList(chair1, chair2));
        // add a mutable part
        list.add(chair3);
        // remove from the mutable part by an index
        Assert.assertEquals(chair3, list.remove(2));

        Product[] expected = {chair1, chair2};
        Assert.assertArrayEquals(expected, list.toArray());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void removeByIndexWhenIndexOutOfBoundsTest() {
        Product chair1 = new GamingChair("ASUS Super model", 100000, 120, true, true);
        Product chair2 = new GamingChair("HomeMade not super model", 400, 75, true, false);
        Product chair3 = new RockingChair("Uncle Sam model", 399.99, 80, 35);
        // add an immutable part
        DoubleBaseList<Product> list = new DoubleBaseList<>(Arrays.asList(chair1, chair2));
        // add a mutable part
        list.add(chair3);
        // remove by a wrong index
        list.remove(3);
    }

    @Test(expected = IllegalArgumentException.class)
    public void removeByIndexFromImmutablePartTest() {
        Product chair1 = new GamingChair("ASUS Super model", 100000, 120, true, true);
        Product chair2 = new GamingChair("HomeMade not super model", 400, 75, true, false);
        Product chair3 = new RockingChair("Uncle Sam model", 399.99, 80, 35);
        // add an immutable part
        DoubleBaseList<Product> list = new DoubleBaseList<>(Arrays.asList(chair1, chair2));
        // add a mutable part
        list.add(chair3);
        // remove from the immutable part
        list.remove(1);
    }

    @Test
    public void removeAllTest() {
        Product chair1 = new GamingChair("ASUS Super model", 100000, 120, true, true);
        Product chair2 = new GamingChair("HomeMade not super model", 400, 75, true, false);
        Product chair3 = new RockingChair("Uncle Sam model", 399.99, 80, 35);
        // add an immutable part
        DoubleBaseList<Product> list = new DoubleBaseList<>(Arrays.asList(chair1));
        // add a mutable part
        list.addAll(
                Arrays.asList(chair2, chair3, chair2, chair2));
        // remove all from the mutable part
        list.removeAll(
                Arrays.asList(chair2, chair3));

        Product[] expected = {chair1};
        Assert.assertArrayEquals(expected, list.toArray());
    }

    @Test(expected = IllegalArgumentException.class)
    public void removeAllFromImmutablePartTest() {
        Product chair1 = new GamingChair("ASUS Super model", 100000, 120, true, true);
        Product chair2 = new GamingChair("HomeMade not super model", 400, 75, true, false);
        Product chair3 = new RockingChair("Uncle Sam model", 399.99, 80, 35);
        // add an immutable part
        DoubleBaseList<Product> list = new DoubleBaseList<>(Arrays.asList(chair1));
        // add a mutable part
        list.addAll(
                Arrays.asList(chair2, chair3));
        // remove all touching the immutable side
        list.removeAll(
                Arrays.asList(chair1, chair3));
    }

    @Test
    public void retainAllTest() {
        Product chair1 = new GamingChair("ASUS Super model", 100000, 120, true, true);
        Product chair2 = new GamingChair("HomeMade not super model", 400, 75, true, false);
        Product chair3 = new RockingChair("Uncle Sam model", 399.99, 80, 35);
        // add an immutable part
        DoubleBaseList<Product> list = new DoubleBaseList<>(Arrays.asList(chair1));
        // add a mutable part
        list.addAll(
                Arrays.asList(chair2, chair3, chair2, chair2));
        Assert.assertTrue(
                list.retainAll(
                        Arrays.asList(chair1, chair3)));

        Product[] expected = {chair1, chair3};
        Assert.assertArrayEquals(expected, list.toArray());
    }

    @Test
    public void getTest() {
        Product chair1 = new GamingChair("ASUS Super model", 100000, 120, true, true);
        Product chair2 = new GamingChair("HomeMade not super model", 400, 75, true, false);
        // add an immutable part
        DoubleBaseList<Product> list = new DoubleBaseList<>(Arrays.asList(chair1));
        // add a mutable part
        list.add(chair2);
        Assert.assertEquals(chair1, list.get(0));
        Assert.assertEquals(chair2, list.get(1));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void getWhenIndexOutOfBoundsTest() {
        Product chair1 = new GamingChair("ASUS Super model", 100000, 120, true, true);
        Product chair2 = new GamingChair("HomeMade not super model", 400, 75, true, false);
        // add an immutable part
        DoubleBaseList<Product> list = new DoubleBaseList<>(Arrays.asList(chair1));
        // add a mutable part
        list.add(chair2);
        list.get(2);
    }

    @Test
    public void setTest() {
        Product chair1 = new GamingChair("ASUS Super model", 100000, 120, true, true);
        Product chair2 = new GamingChair("HomeMade not super model", 400, 75, true, false);
        Product chair3 = new RockingChair("Uncle Sam model", 399.99, 80, 35);
        // add an immutable part
        DoubleBaseList<Product> list = new DoubleBaseList<>(Arrays.asList(chair1));
        // add a mutable part
        list.add(chair2);
        // set in the mutable part
        Assert.assertEquals(chair2,
                list.set(1, chair3));
        Product[] expected = {chair1, chair3};
        Assert.assertArrayEquals(expected, list.toArray());
    }

    @Test(expected = IllegalArgumentException.class)
    public void setWhenIndexInImmutablePartTest() {
        Product chair1 = new GamingChair("ASUS Super model", 100000, 120, true, true);
        Product chair2 = new GamingChair("HomeMade not super model", 400, 75, true, false);
        // add an immutable part
        DoubleBaseList<Product> list = new DoubleBaseList<>(Arrays.asList(chair1));
        // set in immutable part
        list.set(0, chair2);
    }

    @Test
    public void indexOfTest() {
        Product chair1 = new GamingChair("ASUS Super model", 100000, 120, true, true);
        Product chair2 = new GamingChair("HomeMade not super model", 400, 75, true, false);
        Product chair3 = new RockingChair("Uncle Sam model", 399.99, 80, 35);
        // add an immutable part
        DoubleBaseList<Product> list = new DoubleBaseList<>(Arrays.asList(chair1, chair1));
        // add a mutable part
        list.add(chair2);
        Assert.assertEquals(0, list.indexOf(chair1));
        Assert.assertEquals(2, list.indexOf(chair2));
        Assert.assertEquals(-1, list.indexOf(chair3));
    }

    @Test
    public void lastIndexOfTest() {
        Product chair1 = new GamingChair("ASUS Super model", 100000, 120, true, true);
        Product chair2 = new GamingChair("HomeMade not super model", 400, 75, true, false);
        Product chair3 = new RockingChair("Uncle Sam model", 399.99, 80, 35);
        // add an immutable part
        DoubleBaseList<Product> list = new DoubleBaseList<>(Arrays.asList(chair1, chair1));
        // add a mutable part
        list.add(chair2);
        Assert.assertEquals(1, list.lastIndexOf(chair1));
        Assert.assertEquals(2, list.lastIndexOf(chair2));
        Assert.assertEquals(-1, list.lastIndexOf(chair3));
    }

    @Test
    public void toIndexTest() {
        Product chair1 = new GamingChair("ASUS Super model", 100000, 120, true, true);
        Product chair2 = new GamingChair("HomeMade not super model", 400, 75, true, false);
        // add an immutable part
        DoubleBaseList<Product> list = new DoubleBaseList<>(Arrays.asList(chair1));
        // add a mutable part
        list.addAll(
                Arrays.asList(chair2));

        GamingChair[] actual = new GamingChair[3];
        GamingChair[] expected = {(GamingChair) chair1, (GamingChair) chair2, null};
        Assert.assertArrayEquals(expected, list.toArray(actual));
    }

    @Test
    public void constructorTest() {
        Product chair1 = new GamingChair("ASUS Super model", 100000, 120, true, true);
        DoubleBaseList<Product> list = new DoubleBaseList<>(null);
        list.add(chair1);
        Assert.assertEquals(1, list.size());
    }
}
