package com.epam.rd.command.impl;

import com.epam.rd.command.ICommand;
import com.epam.rd.service.ICartService;
import com.epam.rd.service.impl.CartService;
import com.epam.rd.context.ApplicationContext;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CartListCommand implements ICommand {
    private final Pattern LIST_WITH_PARAMETER_PATTERN = Pattern.compile("^cart list ?([0-9]{1,2})$");
    private ICartService cartService = (CartService) ApplicationContext.getInstance().find("cartService");

    @Override
    public String execute(String command) {
        Matcher listWithParameterMatcher = LIST_WITH_PARAMETER_PATTERN.matcher(command);

        // display full info of thr cart's content
        if (command.equalsIgnoreCase("cart list")) {
            return cartService.getFullStringInfo();
        }
        // display info of the last N elements in the cart
        else if (listWithParameterMatcher.find()) {
            int amount = Integer.parseInt(listWithParameterMatcher.group(1));
            return cartService.getLastElementsInfo(amount);
        }

        return "Unknown parameters of the command 'cart list'";
    }
}