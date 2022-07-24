package com.epam.rd.pojo;

import java.util.Objects;

public class RockingChair extends Chair {
    private int maxRockingAmplitude;

    public RockingChair() {}
    public RockingChair(String name, double price, int maxWeight, int maxRockingAmplitude) {
        super(name, price, maxWeight);
        this.maxRockingAmplitude = maxRockingAmplitude;
    }

    public int getMaxRockingAmplitude() {
        return maxRockingAmplitude;
    }

    public void setMaxRockingAmplitude(int maxRockingAmplitude) {
        this.maxRockingAmplitude = maxRockingAmplitude;
    }

    @Override
    public String toString() {
        return "RockingChair{" +
                "maxRockingAmplitude=" + maxRockingAmplitude +
                ", maxWeight=" + this.getMaxWeight() +
                ", name='" + this.getName() + '\'' +
                ", price=" + this.getPrice() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RockingChair)) return false;
        if (!super.equals(o)) return false;

        RockingChair that = (RockingChair) o;

        return getMaxRockingAmplitude() == that.getMaxRockingAmplitude();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), maxRockingAmplitude);
    }
}