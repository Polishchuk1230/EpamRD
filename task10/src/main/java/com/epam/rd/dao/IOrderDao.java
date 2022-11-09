package com.epam.rd.dao;

import com.epam.rd.entity.Order;

public interface IOrderDao extends IDao {
    boolean save(Order order);
}
