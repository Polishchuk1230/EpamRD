package com.epam.rd.net.socket_controller.impl;

import com.epam.rd.context.ApplicationContext;
import com.epam.rd.net.reflection.GetMapping;
import com.epam.rd.net.reflection.RequestParam;
import com.epam.rd.net.socket_controller.util.HttpResponse;
import com.epam.rd.net.socket_controller.util.StatusCodeHeader;
import com.epam.rd.net.socket_controller.util.TypeContentHeader;
import com.epam.rd.pojo.Product;
import com.epam.rd.service.IProductService;
import com.epam.rd.service.impl.ProductService;
import org.json.JSONObject;

import java.util.Map;

public class HttpController extends AbstractHttpController {
    private IProductService productService = (ProductService) ApplicationContext.getInstance().find("productService");

    @GetMapping("/shop/count")
    public HttpResponse getCount() {
        JSONObject body = new JSONObject(
                Map.of("count", productService.count()));

        return new HttpResponse(StatusCodeHeader.CODE_200)
                .setTypeContent(TypeContentHeader.JSON)
                .setBody(body);
    }

    @GetMapping("/shop/item")
    public HttpResponse getItem(@RequestParam("get_info") String strItemId) {
        Product product = productService.findByStringId(strItemId);
        Map<String, String> temp = product == null ?
                Map.of("error", "Unknown product") :
                Map.of("name", product.getName(), "price", String.valueOf(product.getPrice()));
        JSONObject body = new JSONObject(temp);

        return new HttpResponse(StatusCodeHeader.CODE_200)
                .setTypeContent(TypeContentHeader.JSON)
                .setBody(body);
    }
}
