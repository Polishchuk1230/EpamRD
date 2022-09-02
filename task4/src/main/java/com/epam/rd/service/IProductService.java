package com.epam.rd.service;

import com.epam.rd.pojo.Product;

public interface IProductService {
    String getAllAsString();

    Product findById(int id);

    Product findByStringId(String stringId);

    boolean add(Product product);

    int count();
}
