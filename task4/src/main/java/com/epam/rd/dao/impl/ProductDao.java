package com.epam.rd.dao.impl;

import com.epam.rd.dao.IProductDaoFile;
import com.epam.rd.pojo.Product;
import com.epam.rd.util.Serializer;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class ProductDao implements IProductDaoFile {

    private static final String STORAGE_PATH = "task4/src/main/resources/product_storage.ser";
    private int generatedId;
    Map<Integer, Product> map;

    public ProductDao() {
        HashMap<Integer, Product> storage = Serializer.deserialize(STORAGE_PATH);
        map = storage != null ? storage : new HashMap<>();
        generatedId = ProductDao.findMaxKey(map) + 1;
    }

    @Override
    public List<Product> findAll() {
        return new ArrayList<>(map.values());
    }

    @Override
    public Product findById(int id) {
        return map.get(id);
    }

    @Override
    public boolean add(Product product) {
        if (product.getId() == 0) {
            product.setId(generatedId++);
        } else if (product.getId() >= generatedId) {
            generatedId = product.getId() + 1;
        }
        map.put(product.getId(), product);
        return true;
    }

    @Override
    public void saveData() {
        Serializer.serialize((HashMap<Integer, Product>) map, STORAGE_PATH);
    }

    private static int findMaxKey(Map<Integer, ?> map) {
        int result = 0;
        for (Integer i : map.keySet()) {
            if (result < i) {
                result = i;
            }
        }
        return result;
    }
}
