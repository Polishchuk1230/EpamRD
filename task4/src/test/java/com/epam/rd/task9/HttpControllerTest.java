package com.epam.rd.task9;

import com.epam.rd.net.socket_controller.impl.HttpController;
import com.epam.rd.net.socket_controller.http_response.StatusCodeHeader;
import org.junit.Assert;
import org.junit.Test;

public class HttpControllerTest {

    @Test
    public void badRequestsTest() {
        HttpController controller = new HttpController();
        String expected = StatusCodeHeader.CODE_404.toString();

        // not valid requests
        Assert.assertEquals(expected, controller.processRequest("GET /wrong_request HTTP/1.1")); // there isn't a method annotated with @GetMapping("/wrong_request")
        Assert.assertEquals(expected, controller.processRequest("GET /shop/item??wrong_param=1 HTTP/1.1")); // ??
        Assert.assertEquals(expected, controller.processRequest("GET /shop/item?get_item=1?param2=2 HTTP/1.1")); // wrong params' separator

        // valid requests
        Assert.assertNotEquals(expected, controller.processRequest("GET /shop/count HTTP/1.1"));
        Assert.assertNotEquals(expected, controller.processRequest("GET /shop/item?get_item=1 HTTP/1.1"));
        Assert.assertNotEquals(expected, controller.processRequest("GET /shop/item?get_item=1&not_existing_but_valid_param=2 HTTP/1.1"));
    }
}
