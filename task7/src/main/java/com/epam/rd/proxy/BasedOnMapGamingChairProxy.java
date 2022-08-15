package com.epam.rd.proxy;

import com.epam.rd.pojo.IGamingChair;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

/**
 * IGamingChair proxy instance based on Map<String, Object>
 */
public class BasedOnMapGamingChairProxy implements InvocationHandler {
    private Map<String, Object> map = new HashMap<>();

    public static IGamingChair getInstance() {
        return BasedOnMapGamingChairProxy.getInstance(0, "", 0.,0, false, false);
    }
    public static IGamingChair getInstance(int id, String name, double price, int maxWeight, boolean arms, boolean headrest) {
        return (IGamingChair) Proxy.newProxyInstance(IGamingChair.class.getClassLoader(),
                new Class[] { IGamingChair.class },
                new BasedOnMapGamingChairProxy(id, name, price, maxWeight, arms, headrest));
    }

    private BasedOnMapGamingChairProxy(int id, String name, double price, int maxWeight, boolean arms, boolean headrest) {
        map.put("Id", id);
        map.put("Name", name);
        map.put("Price", price);
        map.put("MaxWeight", maxWeight);
        map.put("Arms", arms);
        map.put("Headrest", headrest);
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
        }

        return null;
    }
}
