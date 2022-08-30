package com.epam.rd.command.impl;

import com.epam.rd.command.ICommand;
import com.epam.rd.service.IProductService;
import com.epam.rd.service.impl.ProductService;
import com.epam.rd.context.ApplicationContext;

public class ProductListCommand implements ICommand {
    private IProductService productService = (ProductService) ApplicationContext.getInstance().find("productService");
    @Override
    public String execute(String command) {

        // displays lull list of the products
        if (command.equals("product list")) {
            return productService.getAllAsString();
        }
        return "Unknown parameters in the command 'product list'.";
    }

    @Override
    public String getInfo() {
        return "product list\n[displays all available products]";
    }
}
