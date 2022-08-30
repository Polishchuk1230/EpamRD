package com.epam.rd.net.socket_controller.impl;

import com.epam.rd.context.ApplicationContext;
import com.epam.rd.net.socket_controller.ISocketController;
import com.epam.rd.pojo.Product;
import com.epam.rd.service.IProductService;
import com.epam.rd.service.impl.ProductService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TcpController implements ISocketController {
    private static final Pattern PATTERN = Pattern.compile("^get (?:count|item=(\\d+))$");

    private IProductService productService = (ProductService) ApplicationContext.getInstance().find("productService");

    @Override
    public String processRequest(String command) {
        Matcher matcher = PATTERN.matcher(command);
        if (!matcher.find()) {
            return "Unknown TCP-request.";
        }

        if (command.startsWith("get count")) {
            return productService.count() + "";
        }
        else if (command.startsWith("get item=")) {
            Product product = productService.findByStringId(matcher.group(1));
            if (product == null) {
                return "Unknown product";
            }
            return String.format("%s | %.2f", product.getName(), product.getPrice());
        }

        return null;
    }
}
