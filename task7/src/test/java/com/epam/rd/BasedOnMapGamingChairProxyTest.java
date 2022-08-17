package com.epam.rd;

import com.epam.rd.pojo.GamingChair;
import com.epam.rd.pojo.IGamingChair;
import com.epam.rd.proxy.BasedOnMapGamingChairProxy;
import org.junit.Assert;
import org.junit.Test;

public class BasedOnMapGamingChairProxyTest {

    @Test
    public void getTest() {
        IGamingChair gamingChair = BasedOnMapGamingChairProxy.getInstance(
                new GamingChair("Asus super model", 777.7, 77, true, false));

        Assert.assertEquals("Asus super model", gamingChair.getName());
        Assert.assertEquals(777.7, gamingChair.getPrice(), 0.00001);
        Assert.assertEquals(77, gamingChair.getMaxWeight());
        Assert.assertTrue(gamingChair.isArms());
        Assert.assertFalse(gamingChair.isHeadrest());
    }

    @Test
    public void setTest() {
        IGamingChair gamingChair = BasedOnMapGamingChairProxy.getInstance(
                new GamingChair());
        gamingChair.setName("Asus super model");
        gamingChair.setPrice(777.7);
        gamingChair.setMaxWeight(77);
        gamingChair.setArms(true);

        Assert.assertEquals("Asus super model", gamingChair.getName());
        Assert.assertEquals(777.7, gamingChair.getPrice(), 0.00001);
        Assert.assertEquals(77, gamingChair.getMaxWeight());
        Assert.assertTrue(gamingChair.isArms());
        Assert.assertFalse(gamingChair.isHeadrest());
    }
}
