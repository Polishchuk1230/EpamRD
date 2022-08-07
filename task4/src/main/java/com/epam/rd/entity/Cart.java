package com.epam.rd.entity;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Entity of a cart.
 * Is a temporary storage before to purchase.
 */
public class Cart {
    private int id;
    private Map<Integer, Integer> items = new LinkedHashMap<>();

    public Cart() {}
    public Cart(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Map<Integer, Integer> getItems() {
        return items;
    }

    public void setItems(Map<Integer, Integer> items) {
        this.items = items;
    }
}
