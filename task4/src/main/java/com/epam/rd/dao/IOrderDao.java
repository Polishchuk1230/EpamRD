package com.epam.rd.dao;

import com.epam.rd.entity.Order;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Interface for the access to orders storage
 */
public interface IOrderDao {
    /**
     * Insert one new Order instance.
     * @param order
     * @return
     */
    boolean insert(Order order);

    /**
     * Find the closest Order after the timePoint.
     * @param time
     * @return
     */
    Order findByTime(LocalDateTime time);

    /**
     * Find all Order objects with keys between two timePoints
     * @param from
     * @param to
     * @return
     */
    List<Order> findByPeriod(LocalDateTime from, LocalDateTime to);
}
