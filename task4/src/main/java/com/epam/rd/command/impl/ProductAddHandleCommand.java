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
    private static final Pattern PATTERN = Pattern.compile(",? ?([\\p{L} ]+) ?= ?(?:\"([\\p{L}\\d ]+)\"|([\\d.]+))");

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
        return Reflection.fillProduct(new GamingChair(), parseMap(parameters));
    }

    @Override
    Product collectRockingChair(String parameters) {
        return Reflection.fillProduct(new RockingChair(), parseMap(parameters));
    }
}
