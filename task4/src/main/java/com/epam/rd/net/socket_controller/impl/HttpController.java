package com.epam.rd.net.socket_controller.impl;

import com.epam.rd.context.ApplicationContext;
import com.epam.rd.net.reflection.GetMapping;
import com.epam.rd.net.reflection.GetMappingHandler;
import com.epam.rd.net.socket_controller.ISocketController;
import com.epam.rd.pojo.Product;
import com.epam.rd.service.IProductService;
import com.epam.rd.service.impl.ProductService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.epam.rd.net.socket_controller.util.HttpResponseHeaders.*;

public class HttpController implements ISocketController {
    private static Logger logger = LogManager.getLogger(HttpController.class);
    private static final Pattern PATTERN = Pattern.compile("^GET (/shop(?:/count|/item\\?get_info=(\\d+))) HTTP/1.1$");

    private IProductService productService = (ProductService) ApplicationContext.getInstance().find("productService");

    @Override
    public String processRequest(String command) {
        if (command == null) {
            return addFirstHeaders400();
        }

        logger.info(command);

        Matcher matcher = PATTERN.matcher(command);
        if (matcher.find()) {
            String path = matcher.group(1);

            String answer = GetMappingHandler.processRequest(this,
                    path,
                    new Object[] { matcher.group(2) });

            if (answer != null) {
                return answer;
            }
        }

        return addFirstHeaders404();
    }

    @GetMapping("/shop/count")
    public String getCount() {
        return addFirstHeaders200(
                String.format("{\"count\":%d}", productService.count()));
    }

    @GetMapping("/shop/item")
    public String getItem(String strItemId) {
        Product product = productService.findByStringId(strItemId);
        if (product == null) {
            return addFirstHeaders200("{\"error\":\"Unknown product\"}");
        }
        return addFirstHeaders200(
                String.format("{\"name\":\"%s\", \"price\":%.2f}", product.getName(), product.getPrice()));
    }
}
