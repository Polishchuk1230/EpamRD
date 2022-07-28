package com.epam.rd;

import com.epam.rd.container.CopyOnWriteListImpl;
import com.epam.rd.pojo.Furniture;
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
        Furniture chair1 = new GamingChair("ASUS Super model", 100000, 120, true, true);
        Furniture chair2 = new GamingChair("HomeMade not super model", 400, 75, true, false);
        Furniture chair3 = new RockingChair("Uncle Sam model", 399.99, 80, 35);
        CopyOnWriteListImpl<Furniture> listImpl = new CopyOnWriteListImpl<>();

        Assert.assertFalse(
                listImpl.addAll(
                        List.of()));

        Assert.assertTrue(
                listImpl.addAll(
                        List.of(chair1, chair2, chair3)));

        Assert.assertTrue(
                listImpl.addAll(
                        List.of(chair3, chair2)));

        Furniture[] expected = {chair1, chair2, chair3, chair3, chair2};
        Assert.assertArrayEquals(expected, listImpl.toArray());
    }

    @Test(expected = NullPointerException.class)
    public void addAllWhenCollectionContainsNullElement() {
        CopyOnWriteListImpl<Furniture> list = new CopyOnWriteListImpl<>();
        ArrayList<Furniture> tempCollection = new ArrayList<>();
        tempCollection.add(null);
        list.addAll(tempCollection);
    }

    @Test
    public void addAllByIndexTest() {
        Furniture chair1 = new GamingChair("ASUS Super model", 100000, 120, true, true);
        Furniture chair2 = new GamingChair("HomeMade not super model", 400, 75, true, false);
        Furniture chair3 = new RockingChair("Uncle Sam model", 399.99, 80, 35);
        CopyOnWriteListImpl<Furniture> listImpl = new CopyOnWriteListImpl<>();

        Assert.assertFalse(
                listImpl.addAll(0,
                        List.of()));

        Assert.assertTrue(
                listImpl.addAll(0,
                        List.of(chair1, chair2)));

        Assert.assertTrue(
                listImpl.addAll(1,
                        List.of(chair3, chair3, chair3)));

        Furniture[] expected = {chair1, chair3, chair3, chair3, chair2};

        Assert.assertArrayEquals(expected, listImpl.toArray());
    }

    @Test
    public void addTest() {
        Furniture chair1 = new GamingChair("ASUS Super model", 100000, 120, true, true);
        Furniture chair2 = new GamingChair("HomeMade not super model", 400, 75, true, false);
        CopyOnWriteListImpl<Furniture> listImpl = new CopyOnWriteListImpl<>();

        listImpl.add(chair1);
        listImpl.add(chair2);

        Furniture[] expected = {chair1, chair2};
        Assert.assertArrayEquals(expected, listImpl.toArray());
    }

    @Test(expected = NullPointerException.class)
    public void addWhenParameterIsNullTest() {
        CopyOnWriteListImpl<Furniture> listImpl = new CopyOnWriteListImpl<>();
        listImpl.add(null);
    }

    @Test
    public void addByIndexTest() {
        Furniture chair1 = new GamingChair("ASUS Super model", 100000, 120, true, true);
        Furniture chair2 = new GamingChair("HomeMade not super model", 400, 75, true, false);
        Furniture chair3 = new RockingChair("Uncle Sam model", 399.99, 80, 35);
        CopyOnWriteListImpl<Furniture> listImpl = new CopyOnWriteListImpl<>();

        listImpl.add(0, chair1);
        listImpl.add(1, chair3);
        listImpl.add(1, chair2);

        Furniture[] expected = {chair1, chair2, chair3};
        Assert.assertArrayEquals(expected, listImpl.toArray());
    }

    @Test(expected = NullPointerException.class)
    public void addByIndexWhenElementIsNullTest() {
        CopyOnWriteListImpl<Furniture> listImpl = new CopyOnWriteListImpl<>();
        listImpl.add(0, null);
    }

    @Test
    public void getTest() {
        Furniture chair1 = new GamingChair("ASUS Super model", 100000, 120, true, true);
        Furniture chair2 = new GamingChair("HomeMade not super model", 400, 75, true, false);
        Furniture chair3 = new RockingChair("Uncle Sam model", 399.99, 80, 35);
        CopyOnWriteListImpl<Furniture> listImpl = new CopyOnWriteListImpl<>();
        listImpl.addAll(
                List.of(chair1, chair2, chair3));

        Assert.assertNotEquals(chair1, listImpl.get(1));
        Assert.assertEquals(chair1, listImpl.get(0));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void getWhenIndexIsInvalidTest() {
        Furniture chair1 = new GamingChair("ASUS Super model", 100000, 120, true, true);
        CopyOnWriteListImpl<Furniture> listImpl = new CopyOnWriteListImpl<>();
        listImpl.add(chair1);
        listImpl.get(1);
    }

    @Test
    public void removeByIndexTest() {
        Furniture chair1 = new GamingChair("ASUS Super model", 100000, 120, true, true);
        Furniture chair2 = new GamingChair("HomeMade not super model", 400, 75, true, false);
        Furniture chair3 = new RockingChair("Uncle Sam model", 399.99, 80, 35);
        CopyOnWriteListImpl<Furniture> listImpl = new CopyOnWriteListImpl<>();
        listImpl.addAll(
                List.of(chair1, chair2, chair3));

        listImpl.remove(1);

        Furniture[] expected = {chair1, chair3};
        Assert.assertArrayEquals(expected, listImpl.toArray());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void removeByIndexWhenIndexIsInvalidTest() {
        CopyOnWriteListImpl<Furniture> listImpl = new CopyOnWriteListImpl<>();
        listImpl.get(0);
    }

    @Test
    public void removeByObjectTest() {
        Furniture chair1 = new GamingChair("ASUS Super model", 100000, 120, true, true);
        Furniture chair2 = new GamingChair("HomeMade not super model", 400, 75, true, false);
        Furniture chair3 = new RockingChair("Uncle Sam model", 399.99, 80, 35);
        CopyOnWriteListImpl<Furniture> listImpl = new CopyOnWriteListImpl<>();
        listImpl.addAll(
                List.of(chair1, chair2));

        Assert.assertTrue(
                listImpl.remove(chair2));

        Assert.assertFalse(
                listImpl.remove(chair3));

        Furniture[] expected = {chair1};
        Assert.assertArrayEquals(expected, listImpl.toArray());
    }

    @Test
    public void iteratorWithFilterTest() {
        Furniture chair1 = new GamingChair("ASUS Super model", 100000, 120, true, true);
        Furniture chair2 = new GamingChair("HomeMade not super model", 400, 75, true, false);
        Furniture chair3 = new RockingChair("Uncle Sam model", 399.99, 80, 35);
        CopyOnWriteListImpl<Furniture> listImpl = new CopyOnWriteListImpl<>();
        listImpl.addAll(
                List.of(chair1, chair2, chair3));

        Iterator<Furniture> iterator =
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
        Furniture chair1 = new GamingChair("ASUS Super model", 100000, 120, true, true);
        Furniture chair2 = new GamingChair("HomeMade not super model", 400, 75, true, false);
        Furniture chair3 = new RockingChair("Uncle Sam model", 399.99, 80, 35);
        CopyOnWriteListImpl<Furniture> list = new CopyOnWriteListImpl<>();
        list.addAll(
                Arrays.asList(chair1, chair2, chair3));
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 25; i++) {
            executorService.execute(
                    () -> {
                        Iterator<Furniture> iterator;
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
        Furniture chair1 = new GamingChair("ASUS Super model", 100000, 120, true, true);
        Furniture chair2 = new GamingChair("HomeMade not super model", 400, 75, true, false);
        Furniture chair3 = new RockingChair("Uncle Sam model", 399.99, 80, 35);
        CopyOnWriteListImpl<Furniture> list = new CopyOnWriteListImpl<>();
        list.addAll(
                Arrays.asList(chair1, chair2, chair3));
        Assert.assertTrue(
                list.retainAll(
                        Arrays.asList(chair1, chair3)));

        Furniture[] expected = {chair1, chair3};
        Assert.assertArrayEquals(expected, list.toArray());
    }
}
