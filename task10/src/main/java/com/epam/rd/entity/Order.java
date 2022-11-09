package com.epam.rd.entity;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;

public class Order {
    private int id;
    private User user;
    private Map<Product, Integer> cart;
    private LocalDateTime time;
    private OrderStatus orderStatus;
    private String statusDescription;

    public int getId() {
        return id;
    }

    public Order setId(int id) {
        this.id = id;
        return this;
    }

    public User getUser() {
        return user;
    }

    public Order setUser(User user) {
        this.user = user;
        return this;
    }

    public Map<Product, Integer> getCart() {
        return cart;
    }

    public Order setCart(Map<Product, Integer> cart) {
        this.cart = Collections.unmodifiableMap(cart);
        return this;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public Order setTime(LocalDateTime time) {
        this.time = time;
        return this;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public Order setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
        return this;
    }

    public String getStatusDescription() {
        return statusDescription;
    }

    public Order setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
        return this;
    }
}
