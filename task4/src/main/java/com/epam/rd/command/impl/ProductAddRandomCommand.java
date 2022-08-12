package com.epam.rd.command.impl;

import com.epam.rd.pojo.GamingChair;
import com.epam.rd.pojo.Product;
import com.epam.rd.pojo.RockingChair;

import java.util.Random;

public class ProductAddRandomCommand extends AbstractProductAddCommand {
    private Random random = new Random();

    @Override
    Product collectGamingChair(String parameters) {
        return new GamingChair("Random model #" + random.nextInt(0, Integer.MAX_VALUE),
                random.nextInt(99,50000) / 100.,
                random.nextInt(20, 200),
                random.nextBoolean(),
                random.nextBoolean());
    }

    @Override
    Product collectRockingChair(String parameters) {
        return new RockingChair("Random model #" + random.nextInt(0, Integer.MAX_VALUE),
                random.nextInt(99,50000) / 100.,
                random.nextInt(20, 200),
                random.nextInt(1, 90));
    }
}
