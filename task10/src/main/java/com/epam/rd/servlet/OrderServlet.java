package com.epam.rd.servlet;

import com.epam.rd.context.ApplicationContext;
import com.epam.rd.context.util.BeanNames;
import com.epam.rd.entity.*;
import com.epam.rd.service.ICartService;
import com.epam.rd.service.IOrderService;
import com.google.gson.Gson;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

@WebServlet("/order")
public class OrderServlet extends HttpServlet {
    private ICartService cartService;
    private IOrderService orderService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        cartService = (ICartService) ApplicationContext.getInstance().getAttribute(BeanNames.CART_SERVICE);
        orderService = (IOrderService) ApplicationContext.getInstance().getAttribute(BeanNames.ORDER_SERVICE);
        super.init(config);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        User user = (User) req.getSession().getAttribute("user");
        if (Objects.isNull(user)) {
            resp.getWriter().write(
                    new Gson().toJson(
                            Map.of(
                                    "result", Boolean.FALSE,
                                    "message", "User isn't authenticated")));
            return;
        }

        Map<Product, Integer> cart = cartService.getCart(user.getId());
        if (cart.isEmpty()) {
            resp.getWriter().write(
                    new Gson().toJson(
                            Map.of(
                                    "result", Boolean.FALSE,
                                    "message", "Your cart is empty")));
            return;
        }

        Order newOrder = new Order()
                .setUser(user)
                .setCart(cart)
                .setTime(LocalDateTime.now())
                .setOrderStatus(OrderStatus.ACCEPTED);

        boolean result = orderService.save(newOrder);
        if (result) {
            cartService.clearCart(user.getId());
        }

        resp.getWriter().write(
                new Gson().toJson(
                        Map.of(
                                "result", Boolean.TRUE,
                                "message", "Order created successfully")));
    }
}
