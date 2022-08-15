package com.epam.rd.command.impl;

import com.epam.rd.pojo.GamingChair;
import com.epam.rd.pojo.Product;
import com.epam.rd.pojo.RockingChair;
import com.epam.rd.util.Reflection;

/**
 * Extension of ProductAddHandleCommand. The only difference is that, this realization uses Reflection.fillProduct
 * overload method version which can handle localized keys in provided parameters.
 */
public class ProductAddHandleLocalizedCommand extends ProductAddHandleCommand {

    @Override
    Product collectGamingChair(String parameters) {
        return Reflection.fillProduct(new GamingChair(), parseMap(parameters), true);
    }

    @Override
    Product collectRockingChair(String parameters) {
        return Reflection.fillProduct(new RockingChair(), parseMap(parameters), true);
    }
}