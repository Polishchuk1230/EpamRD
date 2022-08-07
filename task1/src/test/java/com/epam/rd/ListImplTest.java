package com.epam.rd;

import com.epam.rd.pojo.GamingChair;
import com.epam.rd.pojo.Product;
import com.epam.rd.pojo.RockingChair;
import com.epam.rd.container.ListImpl;
import org.junit.Assert;
import org.junit.Test;

import java.util.Iterator;
import java.util.List;

public class ListImplTest {
    @Test
    public void addAllTest() {
        Product chair1 = new GamingChair("ASUS Super model", 100000, 120, true, true);
        Product chair2 = new GamingChair("HomeMade not super model", 400, 75, true, false);
        Product chair3 = new RockingChair("Uncle Sam model", 399.99, 80, 35);
        ListImpl<Product> listImpl = new ListImpl<>();

        Assert.assertFalse(
                listImpl.addAll(
                        List.of()));

        Assert.assertTrue(
                listImpl.addAll(
                        List.of(chair1, chair2, chair3)));

        Assert.assertTrue(
                listImpl.addAll(
                        List.of(chair1, chair1)));

        Product[] expected = {chair1, chair2, chair3, chair1, chair1};
        Assert.assertArrayEquals(expected, listImpl.toArray());
    }

    @Test
    public void addAllByIndexTest() {
        Product chair1 = new GamingChair("ASUS Super model", 100000, 120, true, true);
        Product chair2 = new GamingChair("HomeMade not super model", 400, 75, true, false);
        Product chair3 = new RockingChair("Uncle Sam model", 399.99, 80, 35);
        ListImpl<Product> listImpl = new ListImpl<>();

        Assert.assertFalse(
                listImpl.addAll(0,
                        List.of()));

        Assert.assertTrue(
                listImpl.addAll(0,
                        List.of(chair1, chair2)));

        Assert.assertTrue(
                listImpl.addAll(1,
                        List.of(chair3, chair3, chair3)));

        Product[] expected = {chair1, chair3, chair3, chair3, chair2};

        Assert.assertArrayEquals( expected, listImpl.toArray() );
    }

    @Test
    public void addTest() {
        Product chair1 = new GamingChair("ASUS Super model", 100000, 120, true, true);
        Product chair2 = new GamingChair("HomeMade not super model", 400, 75, true, false);
        ListImpl<Product> listImpl = new ListImpl<>();

        listImpl.add(chair1);
        listImpl.add(chair2);

        Product[] expected = {chair1, chair2};
        Assert.assertArrayEquals(expected, listImpl.toArray());
    }

    @Test(expected = NullPointerException.class)
    public void addWhenParameterIsNullTest() {
        ListImpl<Product> listImpl = new ListImpl<>();
        listImpl.add(null);
    }

    @Test
    public void addByIndexTest() {
        Product chair1 = new GamingChair("ASUS Super model", 100000, 120, true, true);
        Product chair2 = new GamingChair("HomeMade not super model", 400, 75, true, false);
        Product chair3 = new RockingChair("Uncle Sam model", 399.99, 80, 35);
        ListImpl<Product> listImpl = new ListImpl<>();

        listImpl.add(0, chair1);
        listImpl.add(1, chair3);
        listImpl.add(1, chair2);

        Product[] expected = {chair1, chair2, chair3};
        Assert.assertArrayEquals(expected, listImpl.toArray());
    }

    @Test(expected = NullPointerException.class)
    public void addByIndexWhenElementIsNullTest() {
        ListImpl<Product> listImpl = new ListImpl<>();
        listImpl.add(0, null);
    }

    @Test
    public void getTest() {
        Product chair1 = new GamingChair("ASUS Super model", 100000, 120, true, true);
        Product chair2 = new GamingChair("HomeMade not super model", 400, 75, true, false);
        Product chair3 = new RockingChair("Uncle Sam model", 399.99, 80, 35);
        ListImpl<Product> listImpl = new ListImpl<>();
        listImpl.addAll(
                List.of(chair1, chair2, chair3));

        Assert.assertNotEquals(chair1, listImpl.get(1));
        Assert.assertEquals(chair1, listImpl.get(0));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void getWhenIndexIsInvalidTest() {
        Product chair1 = new GamingChair("ASUS Super model", 100000, 120, true, true);
        ListImpl<Product> listImpl = new ListImpl<>();
        listImpl.add(chair1);
        listImpl.get(1);
    }

    @Test
    public void removeByIndexTest() {
        Product chair1 = new GamingChair("ASUS Super model", 100000, 120, true, true);
        Product chair2 = new GamingChair("HomeMade not super model", 400, 75, true, false);
        Product chair3 = new RockingChair("Uncle Sam model", 399.99, 80, 35);
        ListImpl<Product> listImpl = new ListImpl<>();
        listImpl.addAll(
                List.of(chair1, chair2, chair3));

        listImpl.remove(1);

        Product[] expected = {chair1, chair3};
        Assert.assertArrayEquals(expected, listImpl.toArray());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void removeByIndexWhenIndexIsInvalidTest() {
        ListImpl<Product> listImpl = new ListImpl<>();
        listImpl.get(0);
    }

    @Test
    public void removeByObjectTest() {
        Product chair1 = new GamingChair("ASUS Super model", 100000, 120, true, true);
        Product chair2 = new GamingChair("HomeMade not super model", 400, 75, true, false);
        Product chair3 = new RockingChair("Uncle Sam model", 399.99, 80, 35);
        ListImpl<Product> listImpl = new ListImpl<>();
        listImpl.addAll(
                List.of(chair1, chair2));

        Assert.assertTrue(
                listImpl.remove(chair2));

        Assert.assertFalse(
                listImpl.remove(chair3));

        Product[] expected = {chair1};
        Assert.assertArrayEquals(expected, listImpl.toArray());
    }

    @Test
    public void iteratorWithFilterTest() {
        Product chair1 = new GamingChair("ASUS Super model", 100000, 120, true, true);
        Product chair2 = new GamingChair("HomeMade not super model", 400, 75, true, false);
        Product chair3 = new RockingChair("Uncle Sam model", 399.99, 80, 35);
        ListImpl<Product> listImpl = new ListImpl<>();
        listImpl.addAll(
                List.of(chair1, chair2, chair3));

        Iterator<Product> iterator =
                listImpl.filterIterator(
                        item -> item.getPrice() >= 400);

        //the iterator should return chair1, as it is at index 0 ant matches the condition.
        Assert.assertTrue(iterator.hasNext());
        Assert.assertEquals(chair1, iterator.next());

        //the iterator should return chair2, as it is at index 1 ant matches the condition.
        Assert.assertTrue(iterator.hasNext());
        Assert.assertEquals(chair2, iterator.next());

        //the iterator should return nothing, as the element at index 2 doesn't match the condition, and it is the last.
        Assert.assertFalse(iterator.hasNext());
    }
}
