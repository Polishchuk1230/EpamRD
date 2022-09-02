package com.epam.rd.command.impl;

import com.epam.rd.command.ICommand;
import com.epam.rd.pojo.Product;
import com.epam.rd.service.IProductService;
import com.epam.rd.context.ApplicationContext;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class AbstractProductAddCommand implements ICommand {
    private static final Pattern ADD_PRODUCT_PATTERN = Pattern.compile("^product add -t ([a-zA-Z]*)(?: --parameters ([\\p{L}\\d =,.\"]*))?$");
    private IProductService productService = (IProductService) ApplicationContext.getInstance().find("productService");
    private final Map<String, Function<String, Product>> parsers = new HashMap<>();

    {
        parsers.put("GamingChair", this::collectGamingChair);
        parsers.put("RockingChair", this::collectRockingChair);
    }

    @Override
    public String execute(String command) {
        Matcher matcher = ADD_PRODUCT_PATTERN.matcher(command);
        if (!matcher.find()) {
            return getHelp();
        }

        Product newProduct = parseProduct(matcher.group(1), Optional.ofNullable(matcher.group(2)).orElse(""));
        if (newProduct == null) {
            return "Provided parameters don't match to the type " + matcher.group(1) + ". Or the type does not exist.\n" + getHelp();
        }

        productService.add(newProduct);
        return "Successfully added to the products: \n" + newProduct;
    }

    @Override
    public String getInfo() {
        return "product add\n[adds a new product to the shop (This command's sentence depends on the current locale. Type \"product add\" to get more info)]";
    }

    protected Product parseProduct(String productType, String parameters) {
        Function<String, Product> parser = parsers.get(productType);
        if (parser != null) {
            return parser.apply(parameters);
        }
        return null;
    }

    abstract Product collectGamingChair(String parameters);
    abstract Product collectRockingChair(String parameters);
    protected abstract String getHelp();
}