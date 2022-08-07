package com.epam.rd.command.impl;

import com.epam.rd.command.ICommand;
import com.epam.rd.pojo.Product;
import com.epam.rd.service.ICartService;
import com.epam.rd.service.IProductService;
import com.epam.rd.service.impl.CartService;
import com.epam.rd.service.impl.ProductService;
import com.epam.rd.util.ApplicationContext;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CartAddCommand implements ICommand {
    private ICartService cartService = (CartService) ApplicationContext.getInstance().find("cartService");
    private IProductService productService = (ProductService) ApplicationContext.getInstance().find("productService");
    private final Pattern ADD_WITH_AMOUNT_PATTERN = Pattern.compile("^cart add -id ?([0-9]{1,5}) -x ?([0-9]{1,2})$");
    @Override
    public String execute(String command) {
        Matcher addWithAmountMatcher = ADD_WITH_AMOUNT_PATTERN.matcher(command);

        // add some product to the cart
        if (addWithAmountMatcher.find()) {
            int productId = Integer.parseInt(addWithAmountMatcher.group(1));
            int amount = Integer.parseInt(addWithAmountMatcher.group(2));

            Product product = productService.findById(productId);

            if (product == null) {
                return "Invalid PRODUCT_ID";
            }
            else if (cartService.add(productId, amount)) {
                return "Successfully added to the cart: " + amount + " x " + productService.findById(productId).getName();
            }
        }

        return "Unknown parameters of the command 'cart add'";
    }
}
