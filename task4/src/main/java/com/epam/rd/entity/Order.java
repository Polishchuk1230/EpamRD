package com.epam.rd.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Entity for an order.
 */
public class Order {
    private int id;
    private LocalDateTime timePoint;
    private List<OrderItem> items;

    public Order() {}
    public Order(int id, LocalDateTime timePoint, List<OrderItem> items) {
        this.id = id;
        this.timePoint = timePoint;
        this.items = items;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getTimePoint() {
        return timePoint;
    }

    public void setTimePoint(LocalDateTime timePoint) {
        this.timePoint = timePoint;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return getTimePoint() + "\n" + getItems().stream()
                        .map(orderItem -> orderItem.getNumber() + " x " + orderItem.getProduct().toString())
                        .collect(Collectors.joining("\n")) + "\n" +
                "Sum prize: " + getItems().stream()
                .mapToDouble(orderItem -> orderItem.getNumber() * orderItem.getProduct().getPrice())
                .sum();
    }
}
