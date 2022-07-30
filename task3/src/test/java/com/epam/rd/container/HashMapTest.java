package com.epam.rd.container;

import com.epam.rd.pojo.Furniture;
import com.epam.rd.pojo.GamingChair;
import com.epam.rd.pojo.RockingChair;
import com.epam.rd.util.StringHasherOnFirst4;
import com.epam.rd.util.StringHasherOnLength;
import org.junit.Test;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/*
   In this test we see that HashMap collection keeps its elements in an array of Node-objects called buckets.
   Elements 'sorted' into these buckets by the index formula = keys' hash codes & (amount of buckets - 1)
 */
public class HashMapTest {
    Furniture chair1 = new GamingChair("ASUS Super model", 100000, 120, true, true);
    Furniture chair2 = new GamingChair("HomeMade not super model", 400, 75, true, false);
    Furniture chair3 = new RockingChair("Uncle Sam model", 399.99, 80, 35);
    Furniture chair4 = new RockingChair("Home best choice", 550, 88, 20);

    @Test
    public void hashOnLengthTest() {
        HashMap<StringHasherOnLength, Furniture> map = new HashMap<>();
        map.put(new StringHasherOnLength(chair1.getName()), chair1);
        map.put(new StringHasherOnLength(chair2.getName()), chair2);
        map.put(new StringHasherOnLength(chair3.getName()), chair3);
        map.put(new StringHasherOnLength(chair4.getName()), chair4);

        System.out.println("\nFrom HashMap (hash on length)");
        Iterator<Map.Entry<StringHasherOnLength, Furniture>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<StringHasherOnLength, Furniture> temp = iterator.next();
            System.out.println("keyHash = " + temp.getKey().hashCode() + ", value = " + temp.getValue());
        }
    }

    @Test
    public void hashOnFirst4Test() {
        HashMap<StringHasherOnFirst4, Furniture> map = new HashMap<>();
        map.put(new StringHasherOnFirst4(chair1.getName()), chair1);
        map.put(new StringHasherOnFirst4(chair2.getName()), chair2);
        map.put(new StringHasherOnFirst4(chair3.getName()), chair3);
        map.put(new StringHasherOnFirst4(chair4.getName()), chair4);

        System.out.println("\nFrom HashMap (hash on first 4)");
        Iterator<Map.Entry<StringHasherOnFirst4, Furniture>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<StringHasherOnFirst4, Furniture> temp = iterator.next();
            System.out.println("keyHash = " + temp.getKey().hashCode() + ", value = " + temp.getValue());
        }
    }
}
