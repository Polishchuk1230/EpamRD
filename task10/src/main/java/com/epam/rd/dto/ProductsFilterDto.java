package com.epam.rd.dto;

import java.util.LinkedHashMap;
import java.util.Map;

public class ProductsFilterDto {
    private double minPrice;
    private double maxPrice;
    private Map<String, String[]> stringFilters = new LinkedHashMap<>(Map.of(
            "name", new String[0],
            "supplier", new String[0],
            "category", new String[0]
    ));

    public ProductsFilterDto() {
    }

    public ProductsFilterDto(double minPrice, double maxPrice) {
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
    }

    public double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(double minPrice) {
        this.minPrice = minPrice;
    }

    public double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public Map<String, String[]> getStringFilters() {
        return stringFilters;
    }
}
