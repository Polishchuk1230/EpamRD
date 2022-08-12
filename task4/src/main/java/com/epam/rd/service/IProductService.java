package com.epam.rd.service;

import com.epam.rd.pojo.Product;

public interface IProductService {
    String getAllAsString();
    Product findById(int id);
    boolean add(Product product);
}
