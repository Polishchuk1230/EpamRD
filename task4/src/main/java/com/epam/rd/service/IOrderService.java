package com.epam.rd.service;

import com.epam.rd.entity.Cart;
import com.epam.rd.entity.Order;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Interface for order services
 */
public interface IOrderService {
    /**
     * This method creates a new order of the content of the cart, adds this order to a storage via IOrderDao and returns that
     * Order instance. And also it clears the cart.
     * @return
     */
    Order create(Cart cart);

    /**
     * This method should find the first order that was created after the provided date.
     * @param timePoint
     * @return
     */
    Order findByDate(LocalDateTime timePoint);

    /**
     * This method should return all orders that were created between the provided dates.
     * @param from
     * @param to
     * @return
     */
    List<Order> findByPeriod(LocalDateTime from, LocalDateTime to);
}
