package com.epam.rd.service;

import com.epam.rd.entity.Cart;

/**
 * Interface for cart services
 */
public interface ICartService {
    boolean add(int productId, int number);
    Cart find();

    /**
     * Clear the cart
     * @return
     */
    boolean clear();

    /**
     * Get String representation of the last N elements of the cart.
     * @param n
     * @return
     */
    String getLastElementsInfo(int n);

    /**
     * Get String representation of the whole content of the cart.
     * @return
     */
    String getFullStringInfo();
}
