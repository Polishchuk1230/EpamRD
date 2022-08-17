package com.epam.rd.pojo;

import com.epam.rd.annotation.ProductField;

import java.util.Objects;

public class Chair extends Product {
    @ProductField("CHAIR_MAX_WEIGHT")
    private int maxWeight;

    public Chair() {}

    public Chair(String name, double price, int maxWeight) {
        super(name, price);
        this.setMaxWeight(maxWeight);
    }

    public Chair(int id, String name, double price, int maxWeight) {
        super(id, name, price);
        this.maxWeight = maxWeight;
    }

    public int getMaxWeight() {
        return maxWeight;
    }

    public void setMaxWeight(int maxWeight) {
        this.maxWeight = maxWeight;
    }

    @Override
    public String toString() {
        return "Chair{" +
                "maxWeight=" + maxWeight +
                ", name='" + this.getName() + '\'' +
                ", price=" + this.getPrice() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Chair)) return false;
        if (!super.equals(o)) return false;

        Chair chair = (Chair) o;

        return getMaxWeight() == chair.getMaxWeight();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), maxWeight);
    }
}
