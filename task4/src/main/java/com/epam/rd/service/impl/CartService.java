package com.epam.rd.service.impl;

import com.epam.rd.dao.ICartDao;
import com.epam.rd.entity.Cart;
import com.epam.rd.pojo.Product;
import com.epam.rd.service.ICartService;
import com.epam.rd.service.IProductService;

import java.util.Map;
import java.util.stream.Collectors;

public class CartService implements ICartService {
    private IProductService productService;
    private ICartDao cartDao;

    public CartService(ICartDao cartDao, IProductService productService) {
        this.productService = productService;
        this.cartDao = cartDao;
    }

    @Override
    public boolean add(int id, int number) {
        Cart cart = cartDao.find();
        Product product = productService.findById(id);
        if (product != null) {
            cart.getItems().put(product.getId(), number);
            if (cartDao.update(cart)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Cart find() {
        return cartDao.find();
    }

    @Override
    public boolean clear() {
        Cart cart = cartDao.find();
        Map<Integer, Integer> items = cart.getItems();

        items.clear();
        cart.setItems(items);
        cartDao.update(cart);
        return true;
    }

    /**
     * Returns String representation of all the cart content at the moment.
     * @return
     */
    @Override
    public String getFullStringInfo() {
        return cartDao.find().getItems().entrySet().stream()
                .map(entry -> entry.getValue() + " x " + productService.findById(entry.getKey()))
                .collect(Collectors.joining("\n"));
    }

    /**
     * Returns String representation of the last N elements in the cart.
     * @param n last elements to show
     * @return
     */
    @Override
    public String getLastElementsInfo(int n) {
        Cart cart = cartDao.find();
        return cartDao.find().getItems().entrySet().stream()
                .skip(Math.max(0, cart.getItems().size() - n))
                .map(entry -> entry.getValue() + " x " + productService.findById(entry.getKey()))
                .collect(Collectors.joining("\n"));
    }
}
