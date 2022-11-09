package com.epam.rd.service.impl;

import com.epam.rd.dao.IOrderDao;
import com.epam.rd.entity.Order;
import com.epam.rd.service.IOrderService;

public class OrderService implements IOrderService {
    private IOrderDao orderDao;

    public OrderService(IOrderDao orderDao) {
        this.orderDao = orderDao;
    }

    @Override
    public boolean save(Order order) {
        return orderDao.save(order);
    }
}
