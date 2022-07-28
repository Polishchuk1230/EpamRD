package com.epam.rd;

import com.epam.rd.container.DoubleBaseList;
import com.epam.rd.pojo.Furniture;
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
        Furniture chair1 = new GamingChair("ASUS Super model", 100000, 120, true, true);
        Furniture chair2 = new GamingChair("HomeMade not super model", 400, 75, true, false);
        // create a list with immutable part
        DoubleBaseList<Furniture> list = new DoubleBaseList<>(Arrays.asList(chair1));
        // add a mutable part
        list.add(chair2);

        Iterator<Furniture> iterator = list.iterator();
        Assert.assertTrue(iterator.hasNext());
        Assert.assertEquals(chair1, iterator.next());
        Assert.assertTrue(iterator.hasNext());
        Assert.assertEquals(chair2, iterator.next());
    }

    @Test(expected = ConcurrentModificationException.class)
    public void iteratorWithConcurrentModificationTest() {
        Furniture chair1 = new GamingChair("ASUS Super model", 100000, 120, true, true);
        Furniture chair2 = new GamingChair("HomeMade not super model", 400, 75, true, false);
        Furniture chair3 = new RockingChair("Uncle Sam model", 399.99, 80, 35);
        // create a list with immutable part
        DoubleBaseList<Furniture> list = new DoubleBaseList<>(Arrays.asList(chair1));
        // add a mutable part
        list.add(chair2);

        Iterator<Furniture> iterator = list.iterator();
        Assert.assertTrue(iterator.hasNext());
        Assert.assertEquals(chair1, iterator.next());

        list.add(1, chair3);

        if (iterator.hasNext()) {
            iterator.next();
        }
    }

    @Test
    public void addTest() {
        Furniture chair1 = new GamingChair("ASUS Super model", 100000, 120, true, true);
        Furniture chair2 = new GamingChair("HomeMade not super model", 400, 75, true, false);
        // create a list with immutable part
        DoubleBaseList<Furniture> list = new DoubleBaseList<>(Arrays.asList(chair1));
        // add a mutable part
        Assert.assertTrue(
                list.add(chair2));
        Furniture[] expected = {chair1, chair2};
        Assert.assertArrayEquals(expected, list.toArray());
    }

    @Test(expected = NullPointerException.class)
    public void addWhenElementIsNull() {
        DoubleBaseList<Furniture> list = new DoubleBaseList<>(null);
        list.add(null);
    }

    @Test
    public void addByIndexTest() {
        Furniture chair1 = new GamingChair("ASUS Super model", 100000, 120, true, true);
        Furniture chair2 = new GamingChair("HomeMade not super model", 400, 75, true, false);
        Furniture chair3 = new RockingChair("Uncle Sam model", 399.99, 80, 35);
        DoubleBaseList<Furniture> list = new DoubleBaseList<>(null);
        // add a mutable part
        list.add(0, chair1);
        list.add(1, chair2);
        list.add(2, chair3);
        list.add(1, chair3);

        Furniture[] expected = {chair1, chair3, chair2, chair3};
        Assert.assertArrayEquals(expected, list.toArray());
    }

    @Test(expected = IllegalArgumentException.class)
    public void addWhenIndexOnImmutablePart() {
        Furniture chair1 = new GamingChair("ASUS Super model", 100000, 120, true, true);
        Furniture chair2 = new GamingChair("HomeMade not super model", 400, 75, true, false);
        DoubleBaseList<Furniture> list = new DoubleBaseList<>(Arrays.asList(chair1));
        // try to change the immutable part
        list.add(0, chair2);
    }

    @Test(expected = NullPointerException.class)
    public void addByIndexWhenElementIsNull() {
        DoubleBaseList<Furniture> list = new DoubleBaseList<>(null);
        list.add(0, null);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void addWhenIndexIsWrong() {
        Furniture chair1 = new GamingChair("ASUS Super model", 100000, 120, true, true);
        DoubleBaseList<Furniture> list = new DoubleBaseList<>(null);
        list.add(1, chair1);
    }

    @Test
    public void addAllTest() {
        Furniture chair1 = new GamingChair("ASUS Super model", 100000, 120, true, true);
        Furniture chair2 = new GamingChair("HomeMade not super model", 400, 75, true, false);
        Furniture chair3 = new RockingChair("Uncle Sam model", 399.99, 80, 35);
        // add an immutable part
        DoubleBaseList<Furniture> list = new DoubleBaseList<>(Arrays.asList(chair1, chair2));
        // add a mutable part
        list.addAll(
                Arrays.asList(chair3, chair1));

        Furniture[] expected = {chair1, chair2, chair3, chair1};
        Assert.assertArrayEquals(expected, list.toArray());
    }

    @Test
    public void addAllByIndexTest() {
        Furniture chair1 = new GamingChair("ASUS Super model", 100000, 120, true, true);
        Furniture chair2 = new GamingChair("HomeMade not super model", 400, 75, true, false);
        Furniture chair3 = new RockingChair("Uncle Sam model", 399.99, 80, 35);
        // add an immutable part
        DoubleBaseList<Furniture> list = new DoubleBaseList<>(Arrays.asList(chair1, chair2));
        // add a mutable part
        list.addAll(2,
                Arrays.asList(chair3, chair1));

        Furniture[] expected = {chair1, chair2, chair3, chair1};
        Assert.assertArrayEquals(expected, list.toArray());
    }

    @Test(expected = IllegalArgumentException.class)
    public void addAllWhenIndexOnImmutablePart() {
        Furniture chair1 = new GamingChair("ASUS Super model", 100000, 120, true, true);
        Furniture chair2 = new GamingChair("HomeMade not super model", 400, 75, true, false);
        Furniture chair3 = new RockingChair("Uncle Sam model", 399.99, 80, 35);
        // add an immutable part
        DoubleBaseList<Furniture> list = new DoubleBaseList<>(Arrays.asList(chair1, chair2));
        // add a mutable part touching immutable side
        list.addAll(1,
                Arrays.asList(chair3, chair1));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void addAllWhenIndexOutOfBounds() {
        Furniture chair1 = new GamingChair("ASUS Super model", 100000, 120, true, true);
        Furniture chair2 = new GamingChair("HomeMade not super model", 400, 75, true, false);
        Furniture chair3 = new RockingChair("Uncle Sam model", 399.99, 80, 35);
        // add an immutable part
        DoubleBaseList<Furniture> list = new DoubleBaseList<>(Arrays.asList(chair1, chair2));
        // add a mutable part by wrong index
        list.addAll(3,
                Arrays.asList(chair3, chair1));
    }

    @Test
    public void removeTest() {
        Furniture chair1 = new GamingChair("ASUS Super model", 100000, 120, true, true);
        Furniture chair2 = new GamingChair("HomeMade not super model", 400, 75, true, false);
        Furniture chair3 = new RockingChair("Uncle Sam model", 399.99, 80, 35);
        // add an immutable part
        DoubleBaseList<Furniture> list = new DoubleBaseList<>(Arrays.asList(chair1, chair2));
        // add a mutable part
        list.add(chair3);
        // remove from the mutable part
        Assert.assertTrue(
                list.remove(chair3));

        Furniture[] expected = {chair1, chair2};
        Assert.assertArrayEquals(expected, list.toArray());
    }

    @Test(expected = IllegalArgumentException.class)
    public void removeWhenObjectInImmutablePartTest() {
        Furniture chair1 = new GamingChair("ASUS Super model", 100000, 120, true, true);
        Furniture chair2 = new GamingChair("HomeMade not super model", 400, 75, true, false);
        // add an immutable part
        DoubleBaseList<Furniture> list = new DoubleBaseList<>(Arrays.asList(chair1, chair2));
        // remove from the immutable part
        list.remove(chair2);
    }

    @Test
    public void removeByIndexTest() {
        Furniture chair1 = new GamingChair("ASUS Super model", 100000, 120, true, true);
        Furniture chair2 = new GamingChair("HomeMade not super model", 400, 75, true, false);
        Furniture chair3 = new RockingChair("Uncle Sam model", 399.99, 80, 35);
        // add an immutable part
        DoubleBaseList<Furniture> list = new DoubleBaseList<>(Arrays.asList(chair1, chair2));
        // add a mutable part
        list.add(chair3);
        // remove from the mutable part by an index
        Assert.assertEquals(chair3, list.remove(2));

        Furniture[] expected = {chair1, chair2};
        Assert.assertArrayEquals(expected, list.toArray());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void removeByIndexWhenIndexOutOfBoundsTest() {
        Furniture chair1 = new GamingChair("ASUS Super model", 100000, 120, true, true);
        Furniture chair2 = new GamingChair("HomeMade not super model", 400, 75, true, false);
        Furniture chair3 = new RockingChair("Uncle Sam model", 399.99, 80, 35);
        // add an immutable part
        DoubleBaseList<Furniture> list = new DoubleBaseList<>(Arrays.asList(chair1, chair2));
        // add a mutable part
        list.add(chair3);
        // remove by a wrong index
        list.remove(3);
    }

    @Test(expected = IllegalArgumentException.class)
    public void removeByIndexFromImmutablePartTest() {
        Furniture chair1 = new GamingChair("ASUS Super model", 100000, 120, true, true);
        Furniture chair2 = new GamingChair("HomeMade not super model", 400, 75, true, false);
        Furniture chair3 = new RockingChair("Uncle Sam model", 399.99, 80, 35);
        // add an immutable part
        DoubleBaseList<Furniture> list = new DoubleBaseList<>(Arrays.asList(chair1, chair2));
        // add a mutable part
        list.add(chair3);
        // remove from the immutable part
        list.remove(1);
    }

    @Test
    public void removeAllTest() {
        Furniture chair1 = new GamingChair("ASUS Super model", 100000, 120, true, true);
        Furniture chair2 = new GamingChair("HomeMade not super model", 400, 75, true, false);
        Furniture chair3 = new RockingChair("Uncle Sam model", 399.99, 80, 35);
        // add an immutable part
        DoubleBaseList<Furniture> list = new DoubleBaseList<>(Arrays.asList(chair1));
        // add a mutable part
        list.addAll(
                Arrays.asList(chair2, chair3, chair2, chair2));
        // remove all from the mutable part
        list.removeAll(
                Arrays.asList(chair2, chair3));

        Furniture[] expected = {chair1};
        Assert.assertArrayEquals(expected, list.toArray());
    }

    @Test(expected = IllegalArgumentException.class)
    public void removeAllFromImmutablePartTest() {
        Furniture chair1 = new GamingChair("ASUS Super model", 100000, 120, true, true);
        Furniture chair2 = new GamingChair("HomeMade not super model", 400, 75, true, false);
        Furniture chair3 = new RockingChair("Uncle Sam model", 399.99, 80, 35);
        // add an immutable part
        DoubleBaseList<Furniture> list = new DoubleBaseList<>(Arrays.asList(chair1));
        // add a mutable part
        list.addAll(
                Arrays.asList(chair2, chair3));
        // remove all touching the immutable side
        list.removeAll(
                Arrays.asList(chair1, chair3));
    }

    @Test
    public void retainAllTest() {
        Furniture chair1 = new GamingChair("ASUS Super model", 100000, 120, true, true);
        Furniture chair2 = new GamingChair("HomeMade not super model", 400, 75, true, false);
        Furniture chair3 = new RockingChair("Uncle Sam model", 399.99, 80, 35);
        // add an immutable part
        DoubleBaseList<Furniture> list = new DoubleBaseList<>(Arrays.asList(chair1));
        // add a mutable part
        list.addAll(
                Arrays.asList(chair2, chair3, chair2, chair2));
        Assert.assertTrue(
                list.retainAll(
                        Arrays.asList(chair1, chair3)));

        Furniture[] expected = {chair1, chair3};
        Assert.assertArrayEquals(expected, list.toArray());
    }

    @Test
    public void getTest() {
        Furniture chair1 = new GamingChair("ASUS Super model", 100000, 120, true, true);
        Furniture chair2 = new GamingChair("HomeMade not super model", 400, 75, true, false);
        // add an immutable part
        DoubleBaseList<Furniture> list = new DoubleBaseList<>(Arrays.asList(chair1));
        // add a mutable part
        list.add(chair2);
        Assert.assertEquals(chair1, list.get(0));
        Assert.assertEquals(chair2, list.get(1));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void getWhenIndexOutOfBoundsTest() {
        Furniture chair1 = new GamingChair("ASUS Super model", 100000, 120, true, true);
        Furniture chair2 = new GamingChair("HomeMade not super model", 400, 75, true, false);
        // add an immutable part
        DoubleBaseList<Furniture> list = new DoubleBaseList<>(Arrays.asList(chair1));
        // add a mutable part
        list.add(chair2);
        list.get(2);
    }

    @Test
    public void setTest() {
        Furniture chair1 = new GamingChair("ASUS Super model", 100000, 120, true, true);
        Furniture chair2 = new GamingChair("HomeMade not super model", 400, 75, true, false);
        Furniture chair3 = new RockingChair("Uncle Sam model", 399.99, 80, 35);
        // add an immutable part
        DoubleBaseList<Furniture> list = new DoubleBaseList<>(Arrays.asList(chair1));
        // add a mutable part
        list.add(chair2);
        // set in the mutable part
        Assert.assertEquals(chair2,
                list.set(1, chair3));
        Furniture[] expected = {chair1, chair3};
        Assert.assertArrayEquals(expected, list.toArray());
    }

    @Test(expected = IllegalArgumentException.class)
    public void setWhenIndexInImmutablePartTest() {
        Furniture chair1 = new GamingChair("ASUS Super model", 100000, 120, true, true);
        Furniture chair2 = new GamingChair("HomeMade not super model", 400, 75, true, false);
        // add an immutable part
        DoubleBaseList<Furniture> list = new DoubleBaseList<>(Arrays.asList(chair1));
        // set in immutable part
        list.set(0, chair2);
    }

    @Test
    public void indexOfTest() {
        Furniture chair1 = new GamingChair("ASUS Super model", 100000, 120, true, true);
        Furniture chair2 = new GamingChair("HomeMade not super model", 400, 75, true, false);
        Furniture chair3 = new RockingChair("Uncle Sam model", 399.99, 80, 35);
        // add an immutable part
        DoubleBaseList<Furniture> list = new DoubleBaseList<>(Arrays.asList(chair1, chair1));
        // add a mutable part
        list.add(chair2);
        Assert.assertEquals(0, list.indexOf(chair1));
        Assert.assertEquals(2, list.indexOf(chair2));
        Assert.assertEquals(-1, list.indexOf(chair3));
    }

    @Test
    public void lastIndexOfTest() {
        Furniture chair1 = new GamingChair("ASUS Super model", 100000, 120, true, true);
        Furniture chair2 = new GamingChair("HomeMade not super model", 400, 75, true, false);
        Furniture chair3 = new RockingChair("Uncle Sam model", 399.99, 80, 35);
        // add an immutable part
        DoubleBaseList<Furniture> list = new DoubleBaseList<>(Arrays.asList(chair1, chair1));
        // add a mutable part
        list.add(chair2);
        Assert.assertEquals(1, list.lastIndexOf(chair1));
        Assert.assertEquals(2, list.lastIndexOf(chair2));
        Assert.assertEquals(-1, list.lastIndexOf(chair3));
    }

    @Test
    public void toIndexTest() {
        Furniture chair1 = new GamingChair("ASUS Super model", 100000, 120, true, true);
        Furniture chair2 = new GamingChair("HomeMade not super model", 400, 75, true, false);
        // add an immutable part
        DoubleBaseList<Furniture> list = new DoubleBaseList<>(Arrays.asList(chair1));
        // add a mutable part
        list.addAll(
                Arrays.asList(chair2));

        GamingChair[] actual = new GamingChair[3];
        GamingChair[] expected = {(GamingChair) chair1, (GamingChair) chair2, null};
        Assert.assertArrayEquals(expected, list.toArray(actual));
    }

    @Test
    public void constructorTest() {
        Furniture chair1 = new GamingChair("ASUS Super model", 100000, 120, true, true);
        DoubleBaseList<Furniture> list = new DoubleBaseList<>(null);
        list.add(chair1);
        Assert.assertEquals(1, list.size());
    }
}
