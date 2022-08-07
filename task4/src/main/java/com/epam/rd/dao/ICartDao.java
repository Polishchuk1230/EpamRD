package com.epam.rd.dao;

import com.epam.rd.entity.Cart;

/**
 * Interface for the access to carts storage
 */
public interface ICartDao {
    /**
     * Find the cart.
     * @return
     */
    Cart find();

    /**
     * Update the cart.
     * @param cart
     * @return
     */
    boolean update(Cart cart);
}
