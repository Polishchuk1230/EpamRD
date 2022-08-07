package com.epam.rd.command.impl;

import com.epam.rd.command.ICommand;
import com.epam.rd.entity.Cart;
import com.epam.rd.entity.Order;
import com.epam.rd.service.ICartService;
import com.epam.rd.service.IOrderService;
import com.epam.rd.service.impl.CartService;
import com.epam.rd.service.impl.OrderService;
import com.epam.rd.util.ApplicationContext;

public class OrderCreateCommand implements ICommand {
    private IOrderService orderService = (OrderService) ApplicationContext.getInstance().find("orderService");
    private ICartService cartService = (CartService) ApplicationContext.getInstance().find("cartService");

    @Override
    public String execute(String command) {

        // create a new order
        if (command.equalsIgnoreCase("order create")) {
            Cart cart = cartService.find();
            Order order = orderService.create(cart);

            return "Your new order was successfully created. Order info:\n" + order.toString();
        }

        return "Unknown parameters of the command 'order create'";
    }
}
