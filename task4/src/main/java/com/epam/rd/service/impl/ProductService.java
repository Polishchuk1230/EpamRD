package com.epam.rd.service.impl;

import com.epam.rd.dao.IProductDao;
import com.epam.rd.pojo.Product;
import com.epam.rd.service.IProductService;

import java.util.stream.Collectors;

public class ProductService implements IProductService {
    private IProductDao productDao;

    public ProductService(IProductDao productDao) {
        this.productDao = productDao;
    }

    @Override
    public String getAllAsString() {
        return productDao.findAll().stream()
                .map(Product::toString)
                .collect(Collectors.joining("\n"));
    }

    @Override
    public Product findById(int id) {
        return productDao.findById(id);
    }

    @Override
    public boolean add(Product product) {
        return productDao.add(product);
    }

    @Override
    public int count() {
        return productDao.findAll().size();
    }
}