package com.epam.rd.service.impl;

import com.epam.rd.dao.IOrderDao;
import com.epam.rd.entity.Cart;
import com.epam.rd.entity.Order;
import com.epam.rd.pojo.Product;
import com.epam.rd.service.ICartService;
import com.epam.rd.service.IOrderService;
import com.epam.rd.service.IProductService;
import com.epam.rd.entity.OrderItem;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderService implements IOrderService {
    private IProductService productService;
    private ICartService cartService;
    private IOrderDao orderDao;

    public OrderService(IOrderDao orderDao, IProductService productService, ICartService cartService) {
        this.productService = productService;
        this.cartService = cartService;
        this.orderDao = orderDao;
    }

    /**
     * This method creates a new order of the content of the cart, adds this order to a storage via IOrderDao and returns that
     * Order instance. And also it clears the cart.
     * @return
     */
    @Override
    public Order create(Cart cart) {
        List<OrderItem> items = new ArrayList<>();
        cart.getItems().forEach((prodId, amount) -> {
            Product product = productService.findById(prodId);
            OrderItem orderItem = new OrderItem(product, amount);
            items.add(orderItem);
        });

        LocalDateTime timePoint = LocalDateTime.now();
        Order order = new Order(0, timePoint, items);

        if (orderDao.insert(order)) {
            cartService.clear();
        }
        return order;
    }

    @Override
    public Order findByDate(LocalDateTime timePoint) {
        return orderDao.findByTime(timePoint);
    }

    @Override
    public List<Order> findByPeriod(LocalDateTime from, LocalDateTime to) {
        return orderDao.findByPeriod(from, to);
    }
}
