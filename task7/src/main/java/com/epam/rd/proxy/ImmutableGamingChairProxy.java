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
    private final GamingChair gamingChair;

    public static IGamingChair newInstance(GamingChair gamingChair) {
        return (IGamingChair) Proxy.newProxyInstance(IGamingChair.class.getClassLoader(),
                new Class[] { IGamingChair.class },
                new ImmutableGamingChairProxy(gamingChair));
    }

    private ImmutableGamingChairProxy(GamingChair gamingChair) {
        this.gamingChair = gamingChair;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws InvocationTargetException, IllegalAccessException {
        if (method.getName().startsWith("set")) {
            throw new UnsupportedOperationException();
        }
        return method.invoke(gamingChair, args);
    }
}
