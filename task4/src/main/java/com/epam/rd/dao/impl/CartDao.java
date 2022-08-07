package com.epam.rd.dao.impl;

import com.epam.rd.dao.ICartDao;
import com.epam.rd.entity.Cart;

public class CartDao implements ICartDao {
    private Cart cart = new Cart();

    @Override
    public Cart find() {
        return cart;
    }

    @Override
    public boolean update(Cart cart) {
        this.cart = cart;
        return true;
    }
}
