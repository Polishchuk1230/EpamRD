package com.epam.rd.dao;

import com.epam.rd.dto.ProductsFilterDto;
import com.epam.rd.entity.Product;

import java.util.List;

public interface IProductDao extends IDao {

    List<Product> findAllByCriteria(ProductsFilterDto criteria);
}
