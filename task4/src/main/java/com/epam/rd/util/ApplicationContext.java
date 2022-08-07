package com.epam.rd.util;

import com.epam.rd.dao.impl.CartDao;
import com.epam.rd.dao.impl.OrderDao;
import com.epam.rd.dao.impl.ProductDao;
import com.epam.rd.service.impl.CartService;
import com.epam.rd.service.impl.OrderService;
import com.epam.rd.service.impl.ProductService;

import java.util.HashMap;
import java.util.Set;

/**
 * Application scope
 */
public class ApplicationContext {
    private static ApplicationContext instance;
    private final HashMap<String, Object> context = new HashMap<>();

    private ApplicationContext() {
        ProductService productService = new ProductService(new ProductDao());
        CartService cartService = new CartService(new CartDao(), productService);
        OrderService orderService = new OrderService(new OrderDao(), productService, cartService);

        put("productService", productService);
        put("cartService", cartService);
        put("orderService", orderService);
    }

    public static ApplicationContext getInstance() {
        if (instance == null) {
            instance = new ApplicationContext();
        }
        return instance;
    }

    public Object find(String variable) {
        return context.get(variable);
    }

    public Set<String> list() {
        return context.keySet();
    }

    public Object remove(String variable) {
        return context.remove(variable);
    }

    public Object put(String variable, Object object) {
        return context.put(variable, object);
    }
}