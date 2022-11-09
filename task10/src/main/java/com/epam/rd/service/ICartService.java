package com.epam.rd.service;

import com.epam.rd.entity.Product;

import java.util.List;
import java.util.Map;

public interface ICartService {
    boolean addProduct(int userId, int productId, int number);

    boolean removeProduct(int userId, int productId);

    List<Product> getProductsByUserId(int userId);

    int getProductQuantity(int userId, Product product);

    boolean setProductQuantity(int userId, int productId, int newQuantity);

    double getFullPrice(int userId);

    Map<Product, Integer> getCart(int userId);

    boolean clearCart(int userId);
}
