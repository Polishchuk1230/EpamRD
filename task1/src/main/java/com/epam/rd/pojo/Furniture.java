package com.epam.rd.pojo;

import java.util.Objects;

public class Furniture {
    private String name;
    private double price;

    public Furniture() {}
    public Furniture(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "GoodsItem{" +
                "name='" + name + '\'' +
                ", price=" + price +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Furniture)) return false;

        Furniture furniture = (Furniture) o;

        if (Double.valueOf(furniture.getPrice()).equals(getPrice())) return false;
        return getName() != null ? getName().equals(furniture.getName()) : furniture.getName() == null;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price);
    }
}
