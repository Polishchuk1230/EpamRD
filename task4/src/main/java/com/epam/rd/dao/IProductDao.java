package com.epam.rd.dao;

import com.epam.rd.pojo.Product;

import java.util.List;

/**
 * Interface for the access to products storage
 */
public interface IProductDao {
    List<Product> findAll();
    Product findById(int id);
    boolean add(Product product);
}