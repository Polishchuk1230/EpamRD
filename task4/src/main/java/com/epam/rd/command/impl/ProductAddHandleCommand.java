package com.epam.rd.command.impl;

import com.epam.rd.pojo.GamingChair;
import com.epam.rd.pojo.Product;
import com.epam.rd.pojo.RockingChair;
import com.epam.rd.util.Reflection;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProductAddHandleCommand extends AbstractProductAddCommand {
    private static final Pattern PATTERN = Pattern.compile(",? ?([\\p{L} ]+) ?= ?(?:\"([\\p{L}\\d ]+)\"|(true|false|[\\d.]+))");

    protected Map<String, String> parseMap(String parameters) {
        Matcher matcher = PATTERN.matcher(parameters);

        Map<String, String> result = new HashMap<>();
        while (matcher.find()) {
            result.put(matcher.group(1), Optional.ofNullable(matcher.group(2)).orElse(matcher.group(3)));
        }

        return result;
    }

    @Override
    Product collectGamingChair(String parameters) {
        return collectProduct(parameters, new GamingChair());
    }

    @Override
    Product collectRockingChair(String parameters) {
        return collectProduct(parameters, new RockingChair());
    }

    private Product collectProduct(String parameters, Product product) {
        Map<String, String> temp = parseMap(parameters);
        Boolean isLocalized = Optional.ofNullable(temp.remove("locale")).map(Boolean::parseBoolean).orElse(false);
        return Reflection.fillProduct(product, temp, isLocalized);
    }

    @Override
    protected String getHelp() {
        return "Use templates:" +
                "\nproduct add -t GamingChair --parameters locale=true, " + Reflection.getTypedFieldsAsString(GamingChair.class, "id") +
                "\nproduct add -t RockingChair --parameters locale=true, " + Reflection.getTypedFieldsAsString(RockingChair.class, "id");
    }
}
