package com.epam.rd.dao.impl;

import com.epam.rd.dao.IProductDao;
import com.epam.rd.pojo.Product;
import com.epam.rd.pojo.GamingChair;
import com.epam.rd.pojo.RockingChair;

import java.util.*;

public class ProductDao implements IProductDao {
    Map<Integer, Product> map = new HashMap<>();

    {
        map.put(1, new GamingChair(1, "ASUS Super model", 100000, 120, true, true));
        map.put(2, new GamingChair(2,"HomeMade not super model", 400, 75, true, false));
        map.put(3, new RockingChair(3, "Uncle Sam model", 399.99, 80, 35));
    }

    @Override
    public List<Product> findAll() {
        return new ArrayList<>(map.values());
    }

    @Override
    public Product findById(int id) {
        return map.get(id);
    }
}
