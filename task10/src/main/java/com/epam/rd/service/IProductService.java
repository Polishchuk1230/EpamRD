package com.epam.rd.service;

import com.epam.rd.dto.ProductsFilterDto;
import com.epam.rd.entity.Product;

import java.util.List;

public interface IProductService {

    List<Product> findAllByCriteria(ProductsFilterDto criteria);
}
