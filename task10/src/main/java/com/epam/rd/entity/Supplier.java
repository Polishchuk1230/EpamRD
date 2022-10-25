package com.epam.rd.entity;

public class Supplier {
    private int id;
    private String name;

    public Supplier() {
    }

    public Supplier(String name) {
        this.name = name;
    }

    public Supplier(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
