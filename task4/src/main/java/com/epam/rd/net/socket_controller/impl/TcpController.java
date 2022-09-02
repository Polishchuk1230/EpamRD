package com.epam.rd.net.socket_controller.impl;

import com.epam.rd.context.ApplicationContext;
import com.epam.rd.net.reflection.GetMapping;
import com.epam.rd.net.reflection.GetMappingHandler;
import com.epam.rd.net.reflection.RequestParam;
import com.epam.rd.net.socket_controller.ISocketController;
import com.epam.rd.pojo.Product;
import com.epam.rd.service.IProductService;
import com.epam.rd.service.impl.ProductService;

import java.util.Collections;
import java.util.Map;

public class TcpController implements ISocketController {

    private IProductService productService = (ProductService) ApplicationContext.getInstance().find("productService");

    @Override
    public String processRequest(String command) {
        if (!command.contains("=")) {
            return GetMappingHandler.processRequest(this, command, Collections.emptyMap());
        }

        int tempIndex = command.indexOf("=");
        String requestPart = command.substring(0, tempIndex);
        Map<String, String> parameters = Map.of(command.substring(0, tempIndex), command.substring(tempIndex + 1));

        return GetMappingHandler.processRequest(this, requestPart, parameters);
    }

    @GetMapping("get count")
    public String getCount() {
        return productService.count() + "";
    }

    @GetMapping("get item")
    public String getItem(@RequestParam("get item") String id) {
        Product product = productService.findByStringId(id);
            if (product == null) {
                return "Unknown product";
            }
            return String.format("%s | %.2f", product.getName(), product.getPrice());
    }
}
