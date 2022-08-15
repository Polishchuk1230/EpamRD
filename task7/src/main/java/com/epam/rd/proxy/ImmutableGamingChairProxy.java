package com.epam.rd.proxy;

import com.epam.rd.pojo.GamingChair;
import com.epam.rd.pojo.IGamingChair;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * IGamingChair proxy instance based on GamingChair instance.
 * Note: immutable
 */
public class ImmutableGamingChairProxy implements InvocationHandler {
    private final GamingChair value;

    public static IGamingChair newInstance(int id, String name, double price, int maxWeight, boolean arms, boolean headrest) {
        return (IGamingChair) Proxy.newProxyInstance(IGamingChair.class.getClassLoader(),
                new Class[] { IGamingChair.class },
                new ImmutableGamingChairProxy(id, name, price, maxWeight, arms, headrest));
    }

    private ImmutableGamingChairProxy(int id, String name, double price, int maxWeight, boolean arms, boolean headrest) {
        this.value = new GamingChair(id, name, price, maxWeight, arms, headrest);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws InvocationTargetException, IllegalAccessException {
        if (method.getName().startsWith("set")) {
            throw new UnsupportedOperationException();
        }
        return method.invoke(value, args);
    }
}
