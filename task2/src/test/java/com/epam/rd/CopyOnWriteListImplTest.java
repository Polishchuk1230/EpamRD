package com.epam.rd;

import com.epam.rd.container.CopyOnWriteListImpl;
import com.epam.rd.pojo.Product;
import com.epam.rd.pojo.GamingChair;
import com.epam.rd.pojo.RockingChair;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CopyOnWriteListImplTest {
    @Test
    public void addAllTest() {
        Product chair1 = new GamingChair("ASUS Super model", 100000, 120, true, true);
        Product chair2 = new GamingChair("HomeMade not super model", 400, 75, true, false);
        Product chair3 = new RockingChair("Uncle Sam model", 399.99, 80, 35);
        CopyOnWriteListImpl<Product> listImpl = new CopyOnWriteListImpl<>();

        Assert.assertFalse(
                listImpl.addAll(
                        List.of()));

        Assert.assertTrue(
                listImpl.addAll(
                        List.of(chair1, chair2, chair3)));

        Assert.assertTrue(
                listImpl.addAll(
                        List.of(chair3, chair2)));

        Product[] expected = {chair1, chair2, chair3, chair3, chair2};
        Assert.assertArrayEquals(expected, listImpl.toArray());
    }

    @Test(expected = NullPointerException.class)
    public void addAllWhenCollectionContainsNullElement() {
        CopyOnWriteListImpl<Product> list = new CopyOnWriteListImpl<>();
        ArrayList<Product> tempCollection = new ArrayList<>();
        tempCollection.add(null);
        list.addAll(tempCollection);
    }

    @Test
    public void addAllByIndexTest() {
        Product chair1 = new GamingChair("ASUS Super model", 100000, 120, true, true);
        Product chair2 = new GamingChair("HomeMade not super model", 400, 75, true, false);
        Product chair3 = new RockingChair("Uncle Sam model", 399.99, 80, 35);
        CopyOnWriteListImpl<Product> listImpl = new CopyOnWriteListImpl<>();

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

        Assert.assertArrayEquals(expected, listImpl.toArray());
    }

    @Test
    public void addTest() {
        Product chair1 = new GamingChair("ASUS Super model", 100000, 120, true, true);
        Product chair2 = new GamingChair("HomeMade not super model", 400, 75, true, false);
        CopyOnWriteListImpl<Product> listImpl = new CopyOnWriteListImpl<>();

        listImpl.add(chair1);
        listImpl.add(chair2);

        Product[] expected = {chair1, chair2};
        Assert.assertArrayEquals(expected, listImpl.toArray());
    }

    @Test(expected = NullPointerException.class)
    public void addWhenParameterIsNullTest() {
        CopyOnWriteListImpl<Product> listImpl = new CopyOnWriteListImpl<>();
        listImpl.add(null);
    }

    @Test
    public void addByIndexTest() {
        Product chair1 = new GamingChair("ASUS Super model", 100000, 120, true, true);
        Product chair2 = new GamingChair("HomeMade not super model", 400, 75, true, false);
        Product chair3 = new RockingChair("Uncle Sam model", 399.99, 80, 35);
        CopyOnWriteListImpl<Product> listImpl = new CopyOnWriteListImpl<>();

        listImpl.add(0, chair1);
        listImpl.add(1, chair3);
        listImpl.add(1, chair2);

        Product[] expected = {chair1, chair2, chair3};
        Assert.assertArrayEquals(expected, listImpl.toArray());
    }

    @Test(expected = NullPointerException.class)
    public void addByIndexWhenElementIsNullTest() {
        CopyOnWriteListImpl<Product> listImpl = new CopyOnWriteListImpl<>();
        listImpl.add(0, null);
    }

    @Test
    public void getTest() {
        Product chair1 = new GamingChair("ASUS Super model", 100000, 120, true, true);
        Product chair2 = new GamingChair("HomeMade not super model", 400, 75, true, false);
        Product chair3 = new RockingChair("Uncle Sam model", 399.99, 80, 35);
        CopyOnWriteListImpl<Product> listImpl = new CopyOnWriteListImpl<>();
        listImpl.addAll(
                List.of(chair1, chair2, chair3));

        Assert.assertNotEquals(chair1, listImpl.get(1));
        Assert.assertEquals(chair1, listImpl.get(0));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void getWhenIndexIsInvalidTest() {
        Product chair1 = new GamingChair("ASUS Super model", 100000, 120, true, true);
        CopyOnWriteListImpl<Product> listImpl = new CopyOnWriteListImpl<>();
        listImpl.add(chair1);
        listImpl.get(1);
    }

    @Test
    public void removeByIndexTest() {
        Product chair1 = new GamingChair("ASUS Super model", 100000, 120, true, true);
        Product chair2 = new GamingChair("HomeMade not super model", 400, 75, true, false);
        Product chair3 = new RockingChair("Uncle Sam model", 399.99, 80, 35);
        CopyOnWriteListImpl<Product> listImpl = new CopyOnWriteListImpl<>();
        listImpl.addAll(
                List.of(chair1, chair2, chair3));

        listImpl.remove(1);

        Product[] expected = {chair1, chair3};
        Assert.assertArrayEquals(expected, listImpl.toArray());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void removeByIndexWhenIndexIsInvalidTest() {
        CopyOnWriteListImpl<Product> listImpl = new CopyOnWriteListImpl<>();
        listImpl.get(0);
    }

    @Test
    public void removeByObjectTest() {
        Product chair1 = new GamingChair("ASUS Super model", 100000, 120, true, true);
        Product chair2 = new GamingChair("HomeMade not super model", 400, 75, true, false);
        Product chair3 = new RockingChair("Uncle Sam model", 399.99, 80, 35);
        CopyOnWriteListImpl<Product> listImpl = new CopyOnWriteListImpl<>();
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
        CopyOnWriteListImpl<Product> listImpl = new CopyOnWriteListImpl<>();
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

    @Test
    public void iteratorConcurrentTest() {
        Product chair1 = new GamingChair("ASUS Super model", 100000, 120, true, true);
        Product chair2 = new GamingChair("HomeMade not super model", 400, 75, true, false);
        Product chair3 = new RockingChair("Uncle Sam model", 399.99, 80, 35);
        CopyOnWriteListImpl<Product> list = new CopyOnWriteListImpl<>();
        list.addAll(
                Arrays.asList(chair1, chair2, chair3));
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 25; i++) {
            executorService.execute(
                    () -> {
                        Iterator<Product> iterator;
                        synchronized (list) {
                            list.remove(1); // 1 3
                            list.add(2, chair2); // 1 3 2
                            list.remove(chair3); // 1 2
                            list.set(1, chair3); // 1 3
                            list.retainAll(Arrays.asList(chair2, chair3)); // 3
                            iterator = list.iterator(); // <== make a snapshot
                            list.addAll(0, Arrays.asList(chair1, chair2)); // 1 2 3
                        }

                        Assert.assertTrue(
                                iterator.hasNext());
                        Assert.assertEquals(chair3, iterator.next());

                        Assert.assertFalse(
                                iterator.hasNext());
                    });
        }
        executorService.shutdown();
        try {
            executorService.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Assert.fail("The concurrent test was processed too long");
        }
    }

    @Test
    public void retainAllTest() {
        Product chair1 = new GamingChair("ASUS Super model", 100000, 120, true, true);
        Product chair2 = new GamingChair("HomeMade not super model", 400, 75, true, false);
        Product chair3 = new RockingChair("Uncle Sam model", 399.99, 80, 35);
        CopyOnWriteListImpl<Product> list = new CopyOnWriteListImpl<>();
        list.addAll(
                Arrays.asList(chair1, chair2, chair3));
        Assert.assertTrue(
                list.retainAll(
                        Arrays.asList(chair1, chair3)));

        Product[] expected = {chair1, chair3};
        Assert.assertArrayEquals(expected, list.toArray());
    }
}
