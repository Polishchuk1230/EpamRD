package com.epam.rd.entity;

import com.epam.rd.pojo.Product;

public class OrderItem {
    private Product product;
    private int number;

    public OrderItem(Product product, int number) {
        this.product = product;
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return String.format("%d x %s", number, product.toString());
    }
}
