package com.epam.rd;

import com.epam.rd.pojo.GamingChair;
import com.epam.rd.pojo.IGamingChair;
import com.epam.rd.proxy.ImmutableGamingChairProxy;
import org.junit.Assert;
import org.junit.Test;

public class ImmutableGamingChairProxyTest {

    @Test(expected = UnsupportedOperationException.class)
    public void setIdTest() {
        IGamingChair gamingChair = ImmutableGamingChairProxy.newInstance(
                new GamingChair("Asus super model", 777.7, 77, true, false));
        gamingChair.setId(22);
    }

    @Test
    public void getTest() {
        IGamingChair gamingChair = ImmutableGamingChairProxy.newInstance(
                new GamingChair("Asus super model", 777.7, 77, true, false));

        Assert.assertEquals("Asus super model", gamingChair.getName());
        Assert.assertEquals(777.7, gamingChair.getPrice(), 0.00001);
        Assert.assertEquals(77, gamingChair.getMaxWeight());
        Assert.assertTrue(gamingChair.isArms());
        Assert.assertFalse(gamingChair.isHeadrest());
    }
}
