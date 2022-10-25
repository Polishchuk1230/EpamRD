package com.epam.rd.service.impl;

import com.epam.rd.dao.IProductDao;
import com.epam.rd.dto.ProductsFilterDto;
import com.epam.rd.entity.Product;
import com.epam.rd.service.IProductService;

import java.util.List;

public class ProductService implements IProductService {
    private IProductDao productDao;

    public ProductService(IProductDao productDao) {
        this.productDao = productDao;
    }

    @Override
    public List<Product> findAllByCriteria(ProductsFilterDto criteria) {
        return productDao.findAllByCriteria(criteria);
    }
}
