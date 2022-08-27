package com.epam.rd.net.socket_controller.impl;

import com.epam.rd.context.ApplicationContext;
import com.epam.rd.net.socket_controller.ISocketController;
import com.epam.rd.pojo.Product;
import com.epam.rd.service.IProductService;
import com.epam.rd.service.impl.ProductService;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpController implements ISocketController {
    private static final Pattern PATTERN = Pattern.compile("^GET (/shop(?:/count|/item\\?get_info=(\\d+))) HTTP/1.1$");

    private IProductService productService = (ProductService) ApplicationContext.getInstance().find("productService");

    @Override
    public String processRequest(String command) {
        if (command == null) {
            return "HTTP/1.1 400 bad request";
        }

        System.out.println(command);


        Matcher matcher = PATTERN.matcher(command);
        if (matcher.find()) {
            String path = matcher.group(1);

            String headers = """
                    HTTP/1.1 200 OK
                    Content-Type: application/json
                    
                    
                    """;

            if (path.startsWith("/shop/count")) {
                return headers + String.format("{\"count\":%d}", productService.count());
            }
            else if (path.startsWith("/shop/item")) {
                int itemId = Optional.ofNullable(matcher.group(2)).map(Integer::parseInt).orElse(0);
                Product product = Optional.ofNullable(productService.findById(itemId))
                        .orElse(new Product("unknown product", 0.));
                return headers + String.format("{\"name\":\"%s\", \"price\":%.2f}", product.getName(), product.getPrice());
            }

        }

        return "HTTP/1.1 404 not found";
    }
}
