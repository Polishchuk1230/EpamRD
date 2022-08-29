package com.epam.rd.net.socket_controller.impl;

import com.epam.rd.context.ApplicationContext;
import com.epam.rd.net.reflection.GetMapping;
import com.epam.rd.net.reflection.GetMappingHandler;
import com.epam.rd.net.socket_controller.ISocketController;
import com.epam.rd.pojo.Product;
import com.epam.rd.service.IProductService;
import com.epam.rd.service.impl.ProductService;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpController implements ISocketController {
    private static final Pattern PATTERN = Pattern.compile("^GET (/shop(?:/count|/item\\?get_info=(\\d+))) HTTP/1.1$");
    private static final String HEADERS = """
                    HTTP/1.1 200 OK
                    Content-Type: application/json
                    
                    
                    """;

    private IProductService productService = (ProductService) ApplicationContext.getInstance().find("productService");

    @Override
    public String processRequest(String command) {
        if (command == null) {
            return "HTTP/1.1 400 bad request";
        }

        System.out.println(command); // <-- for testing

        Matcher matcher = PATTERN.matcher(command);
        if (matcher.find()) {
            String path = matcher.group(1);

            String answer = null;
            try {
                String param = matcher.group(2);
                if (param == null) {
                    answer = new GetMappingHandler(this).processCommand(path);
                } else {
                    answer = new GetMappingHandler(this).processCommand(path, param);
                }
            } catch (InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }

            if (answer != null) {
                return answer;
            }
        }

        return "HTTP/1.1 404 not found";
    }

    @GetMapping("/shop/count")
    public String getCount() {
        return HEADERS + String.format("{\"count\":%d}", productService.count());
    }

    @GetMapping("/shop/item")
    public String getItem(String strItemId) {
        int itemId = Optional.ofNullable(strItemId).map(Integer::parseInt).orElse(0);
        Product product = Optional.ofNullable(productService.findById(itemId))
                        .orElse(new Product("unknown product", 0.));
        return HEADERS + String.format("{\"name\":\"%s\", \"price\":%.2f}", product.getName(), product.getPrice());
    }
}
