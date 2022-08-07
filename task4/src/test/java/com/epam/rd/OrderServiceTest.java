package com.epam.rd;

import com.epam.rd.dao.impl.OrderDao;
import com.epam.rd.entity.Cart;
import com.epam.rd.entity.Order;
import com.epam.rd.pojo.Product;
import com.epam.rd.pojo.GamingChair;
import com.epam.rd.pojo.RockingChair;
import com.epam.rd.service.IOrderService;
import com.epam.rd.service.impl.CartService;
import com.epam.rd.service.impl.OrderService;
import com.epam.rd.service.impl.ProductService;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Map;


public class OrderServiceTest {

    @Test
    public void createTest() {
        OrderDao orderDao = Mockito.mock(OrderDao.class);
        CartService cartService = Mockito.mock(CartService.class);

        Product product1 = new GamingChair(1, "ASUS Super model", 100000, 120, true, true);
        Product product2 = new GamingChair(2,"HomeMade not super model", 400, 75, true, false);
        Product product3 = new RockingChair(3, "Uncle Sam model", 399.99, 80, 35);

        // prepare the productService
        ProductService productService = Mockito.mock(ProductService.class);
        Mockito.when(productService.findById(1)).thenReturn(product1);
        Mockito.when(productService.findById(2)).thenReturn(product2);
        Mockito.when(productService.findById(3)).thenReturn(product3);

        // prepare the orderService
        IOrderService orderService = new OrderService(orderDao, productService, cartService);

        // prepare the cart
        Cart cart = new Cart();
        Map<Integer, Integer> itemsToPurchase = cart.getItems();
        itemsToPurchase.put(3, 1); // <== add to the cart: 1 x "Uncle Sam model"
        itemsToPurchase.put(1, 2); // <== add to the cart: 2 x "ASUS Super model"

        // create an order
        Order order = orderService.create(cart);

        // test items inside the order
        Assert.assertEquals(1 + " x " + product3, order.getItems().get(0).toString());
        Assert.assertEquals(2 + " x " + product1, order.getItems().get(1).toString());
    }
}
