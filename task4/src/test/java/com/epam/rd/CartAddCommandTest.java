package com.epam.rd;

import com.epam.rd.command.impl.CartAddCommand;
import com.epam.rd.pojo.Product;
import com.epam.rd.pojo.GamingChair;
import com.epam.rd.pojo.RockingChair;
import com.epam.rd.service.impl.CartService;
import com.epam.rd.service.impl.ProductService;
import com.epam.rd.context.ApplicationContext;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class CartAddCommandTest {

    @Test
    public void executeTest() {
        Product product1 = new GamingChair(1, "ASUS Super model", 100000, 120, true, true);
        Product product2 = new GamingChair(2,"HomeMade not super model", 400, 75, true, false);
        Product product3 = new RockingChair(3, "Uncle Sam model", 399.99, 80, 35);

        CartService cartService = Mockito.mock(CartService.class);
        Mockito.when(cartService.add(Mockito.anyInt(), Mockito.anyInt())).thenReturn(true);

        ProductService productService = Mockito.mock(ProductService.class);
        Mockito.when(productService.findById(1)).thenReturn(product1);
        Mockito.when(productService.findById(2)).thenReturn(product2);
        Mockito.when(productService.findById(3)).thenReturn(product3);

        ApplicationContext.getInstance().put("cartService", cartService);
        ApplicationContext.getInstance().put("productService", productService);

        CartAddCommand cartAddCommand = new CartAddCommand();

        Assert.assertEquals("Successfully added to the cart: 2 x ASUS Super model",
                cartAddCommand.execute("cart add -id 1 -x 2"));

        Assert.assertEquals("Successfully added to the cart: 1 x Uncle Sam model",
                cartAddCommand.execute("cart add -id 3 -x 1"));

        Assert.assertEquals("Successfully added to the cart: 12 x HomeMade not super model",
                cartAddCommand.execute("cart add -id 2 -x 12"));

        Assert.assertEquals("Invalid PRODUCT_ID",
                cartAddCommand.execute("cart add -id 0 -x 1"));

        Assert.assertEquals("Unknown parameters of the command 'cart add'",
                cartAddCommand.execute("cart add -id s -x 1"));
    }
}
