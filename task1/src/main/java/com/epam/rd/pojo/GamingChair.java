package com.epam.rd.pojo;

import java.util.Objects;

public class GamingChair extends Chair {
    private boolean arms;
    private boolean headrest;

    public GamingChair() {}
    public GamingChair(String name, double price, int maxWeight, boolean arms, boolean headrest) {
        super(name, price, maxWeight);
        this.arms = arms;
        this.headrest = headrest;
    }

    public GamingChair(int id, String name, double price, int maxWeight, boolean arms, boolean headrest) {
        super(id, name, price, maxWeight);
        this.arms = arms;
        this.headrest = headrest;
    }

    public boolean isArms() {
        return arms;
    }

    public void setArms(boolean arms) {
        this.arms = arms;
    }

    public boolean isHeadrest() {
        return headrest;
    }

    public void setHeadrest(boolean headrest) {
        this.headrest = headrest;
    }

    @Override
    public String toString() {
        return "GamingChair{" +
                "id=" + this.getId() +
                ", arms=" + arms +
                ", headrest=" + headrest +
                ", maxWeight=" + this.getMaxWeight() +
                ", name='" + this.getName() + '\'' +
                ", price=" + this.getPrice() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GamingChair)) return false;
        if (!super.equals(o)) return false;

        GamingChair that = (GamingChair) o;

        if (isArms() != that.isArms()) return false;
        return isHeadrest() == that.isHeadrest();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), arms, headrest);
    }
}
