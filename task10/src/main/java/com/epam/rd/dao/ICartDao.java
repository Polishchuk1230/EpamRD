package com.epam.rd.dao;

import com.epam.rd.entity.Product;

import java.util.Map;

public interface ICartDao extends IDao {
    Map<Product, Integer> findByUserId(int userId);

    boolean addProduct(int userId, int productId, int quantity);

    boolean removeProduct(int userId, int productId);

    boolean changeQuantity(int userId, int productId, int newQuantity);

    boolean clear(int userId);
}
