package com.epam.rd.proxy;

import com.epam.rd.pojo.GamingChair;
import com.epam.rd.pojo.IGamingChair;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * IGamingChair proxy instance based on Map<String, Object>
 */
public class BasedOnMapGamingChairProxy implements InvocationHandler {
    private Map<String, Object> map = new HashMap<>();

    public static IGamingChair getInstance() {
        return BasedOnMapGamingChairProxy.getInstance(new GamingChair());
    }
    public static IGamingChair getInstance(IGamingChair gamingChair) {
        return (IGamingChair) Proxy.newProxyInstance(IGamingChair.class.getClassLoader(),
                new Class[] { IGamingChair.class },
                new BasedOnMapGamingChairProxy(gamingChair));
    }

    private BasedOnMapGamingChairProxy(IGamingChair gamingChair) {
        Arrays.stream(gamingChair.getClass().getMethods())
                .filter(method -> method.getName().startsWith("get") || method.getName().startsWith("is"))
                .forEach(getter -> {
                    try {
                        String temp = getter.getName().startsWith("get") ?
                                getter.getName().substring(3) : getter.getName().substring(2);
                        map.put(temp, getter.invoke(gamingChair));
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        // manage setters
        if (method.getName().startsWith("set")) {
            String fieldName = method.getName().substring(3);
            return map.put(fieldName, args[0]);
        }
        // manage getters
        else if (method.getName().startsWith("get")) {
            String fieldName = method.getName().substring(3);
            return map.get(fieldName);
        } else if (method.getName().startsWith("is")) {
            String fieldName = method.getName().substring(2);
            return map.get(fieldName);
        }

        return null;
    }
}
