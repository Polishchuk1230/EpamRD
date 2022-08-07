package com.epam.rd.container;

import com.epam.rd.pojo.Product;
import com.epam.rd.pojo.GamingChair;
import com.epam.rd.pojo.RockingChair;
import com.epam.rd.util.StringHasherOnFirst4;
import com.epam.rd.util.StringHasherOnLength;
import org.junit.Test;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/*
  In this test we see that LinkedHashMap can iterate its elements in the turn they were added. It is possible because
  every element has a link to the next element.

  But. It is still actual, that LinkedHashMap keep its elements in its hash table.
 */
public class LinkedHashMapTest {
    Product chair1 = new GamingChair("ASUS Super model", 100000, 120, true, true);
    Product chair2 = new GamingChair("HomeMade not super model", 400, 75, true, false);
    Product chair3 = new RockingChair("Uncle Sam model", 399.99, 80, 35);
    Product chair4 = new RockingChair("Home best choice", 550, 88, 20);

    @Test
    public void hashOnLengthTest() {
        LinkedHashMap<StringHasherOnLength, Product> map = new LinkedHashMap<>();
        map.put(new StringHasherOnLength(chair1.getName()), chair1);
        map.put(new StringHasherOnLength(chair2.getName()), chair2);
        map.put(new StringHasherOnLength(chair3.getName()), chair3);
        map.put(new StringHasherOnLength(chair4.getName()), chair4);

        System.out.println("\nFrom LinkedHashMap (hash on length)");
        Iterator<Map.Entry<StringHasherOnLength, Product>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<StringHasherOnLength, Product> temp = iterator.next();
            System.out.println("keyHash = " + temp.getKey().hashCode() + ", value = " + temp.getValue());
        }
    }

    @Test
    public void hashOnFirst4Test() {
        LinkedHashMap<StringHasherOnFirst4, Product> map = new LinkedHashMap<>();
        map.put(new StringHasherOnFirst4(chair1.getName()), chair1);
        map.put(new StringHasherOnFirst4(chair2.getName()), chair2);
        map.put(new StringHasherOnFirst4(chair3.getName()), chair3);
        map.put(new StringHasherOnFirst4(chair4.getName()), chair4);

        System.out.println("\nFrom LinkedHashMap (hash on first 4)");
        Iterator<Map.Entry<StringHasherOnFirst4, Product>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<StringHasherOnFirst4, Product> temp = iterator.next();
            System.out.println("keyHash = " + temp.getKey().hashCode() + ", value = " + temp.getValue());
        }
    }
}
