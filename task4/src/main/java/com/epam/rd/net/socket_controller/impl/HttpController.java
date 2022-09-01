package com.epam.rd.net.socket_controller.impl;

import com.epam.rd.context.ApplicationContext;
import com.epam.rd.net.reflection.GetMapping;
import com.epam.rd.net.reflection.RequestParam;
import com.epam.rd.pojo.Product;
import com.epam.rd.service.IProductService;
import com.epam.rd.service.impl.ProductService;

import static com.epam.rd.net.socket_controller.util.HttpResponseHeaders.RESPONSE_STATUS_CODE_200;
import static com.epam.rd.net.socket_controller.util.HttpResponseHeaders.CONTENT_TYPE_JSON;

public class HttpController extends AbstractHttpController {
    private IProductService productService = (ProductService) ApplicationContext.getInstance().find("productService");

    @GetMapping("/shop/count")
    public String getCount() {
        return RESPONSE_STATUS_CODE_200 +
                CONTENT_TYPE_JSON +
                String.format("\n\r{\"count\":%d}", productService.count());
    }

    @GetMapping("/shop/item")
    public String getItem(@RequestParam("get_info") String strItemId) {
        Product product = productService.findByStringId(strItemId);
        return RESPONSE_STATUS_CODE_200 +
                CONTENT_TYPE_JSON +
                (product == null ? "\n\r{\"error\":\"Unknown product\"}" :
                        String.format("\n\r{\"name\":\"%s\", \"price\":%.2f}", product.getName(), product.getPrice()));
    }
}
