package com.epam.rd.entity;

public enum OrderStatus {
    ACCEPTED(1),
    CONFIRMED(2),
    IN_PROCESS(3),
    SENT(4),
    COMPLETED(5),
    CANCELLED(6);

    private final int id;

    OrderStatus(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
