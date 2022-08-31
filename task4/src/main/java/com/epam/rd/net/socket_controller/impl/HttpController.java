package com.epam.rd.net.socket_controller.impl;

import com.epam.rd.context.ApplicationContext;
import com.epam.rd.net.reflection.GetMapping;
import com.epam.rd.net.reflection.GetMappingHandler;
import com.epam.rd.net.reflection.RequestParam;
import com.epam.rd.net.socket_controller.ISocketController;
import com.epam.rd.net.socket_controller.util.HttpResponseHeaders;
import com.epam.rd.pojo.Product;
import com.epam.rd.service.IProductService;
import com.epam.rd.service.impl.ProductService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class HttpController implements ISocketController {
    private static Logger logger = LogManager.getLogger(HttpController.class);
    private static final Pattern PATTERN = Pattern.compile("^GET (?<path>[\\w/-]*)(?:\\?(?<parameters>((?<=\\?|&)\\w+=\\w+&?)+))? HTTP/1.1$");

    private IProductService productService = (ProductService) ApplicationContext.getInstance().find("productService");

    @Override
    public String processRequest(String command) {
        if (command == null) {
            return HttpResponseHeaders.addFirstHeaders400();
        }

        logger.info(command);

        Matcher matcher = PATTERN.matcher(command);
        if (matcher.find()) {
            String path = matcher.group("path");
            String params = matcher.group("parameters");

            // here I map a String of the following format: aaa=111&bbb=222 to Map-collection
            Map<String, String> parameters = params == null ? new HashMap<>() :
                    Arrays.stream(params.split("&"))
                            .collect(
                                    Collectors.toMap(
                                            str -> str.substring(0, str.indexOf('=')),
                                            str -> str.substring(str.indexOf('=') + 1)));

            String answer = GetMappingHandler.processRequest(this, path, parameters);

            if (answer != null) {
                return answer;
            }
        }

        return HttpResponseHeaders.addFirstHeaders404();
    }

    @GetMapping("/shop/count")
    public String getCount() {
        return HttpResponseHeaders.addFirstHeaders200(
                String.format("Content-Type: application/json\n\r\n\r" +
                        "{\"count\":%d}", productService.count()));
    }

    @GetMapping("/shop/item")
    public String getItem(@RequestParam("get_info") String strItemId) {
        Product product = productService.findByStringId(strItemId);
        if (product == null) {
            return HttpResponseHeaders.addFirstHeaders200(
                    "Content-Type: application/json\n\r" +
                            "\n\r" +
                            "{\"error\":\"Unknown product\"}");
        }
        return HttpResponseHeaders.addFirstHeaders200(
                String.format("Content-Type: application/json\n\r\n\r" +
                        "{\"name\":\"%s\", \"price\":%.2f}", product.getName(), product.getPrice()));
    }

    @GetMapping("/message")
    public String method(@RequestParam("p1") String p1, @RequestParam("p2") String p2) {
        return HttpResponseHeaders.addFirstHeaders200("Content-Type: text/html; charset=UTF-8\n\r\n\r" +
                p1 + ", " + p2);
    }
}
