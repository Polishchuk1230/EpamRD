package com.epam.rd.service.impl;

import com.epam.rd.dao.ICartDao;
import com.epam.rd.entity.Product;
import com.epam.rd.service.ICartService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class CartService implements ICartService {
    private ICartDao cartDao;

    public CartService(ICartDao cartDao) {
        this.cartDao = cartDao;
    }

    @Override
    public boolean addProduct(int userId, int productId, int number) {
        return cartDao.addProduct(userId, productId, number);
    }

    @Override
    public boolean removeProduct(int userId, int productId) {
        return cartDao.removeProduct(userId, productId);
    }

    @Override
    public List<Product> getProductsByUserId(int userId) {
        Map<Product, Integer> cart = cartDao.findByUserId(userId);
        return new ArrayList<>(cart.keySet());
    }

    @Override
    public int getProductQuantity(int userId, Product product) {
        Integer quantity = cartDao.findByUserId(userId).get(product);
        return Optional.ofNullable(quantity).orElse(0);
    }

    @Override
    public boolean setProductQuantity(int userId, int productId, int newQuantity) {
        return cartDao.changeQuantity(userId, productId, newQuantity);
    }

    @Override
    public double getFullPrice(int userId) {
        Map<Product, Integer> cart = cartDao.findByUserId(userId);
        return cart.entrySet().stream()
                .map(entry -> entry.getKey().getPrice().multiply(BigDecimal.valueOf(entry.getValue())))
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO)
                .doubleValue();
    }

    @Override
    public Map<Product, Integer> getCart(int userId) {
        return cartDao.findByUserId(userId);
    }

    @Override
    public boolean clearCart(int userId) {
        return cartDao.clear(userId);
    }
}
