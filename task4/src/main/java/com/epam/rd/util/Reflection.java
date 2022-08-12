package com.epam.rd.util;

import com.epam.rd.pojo.Product;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Reflection {

    /**
     * fill the blank Product instance with parameters
     * @param blankProduct
     * @param parameters
     * @return the product instance filled with parameters or null
     * @param <T>
     */
    public static <T extends Product> T fillProduct(T blankProduct, Map<String, String> parameters) {
        Method[] methods = blankProduct.getClass().getMethods();

        for (Map.Entry<String, String> entry : parameters.entrySet()) {

            String key = entry.getKey();
            Method setter = Arrays.stream(methods)
                    .filter(method -> method.getName().equalsIgnoreCase("set" + key))
                    .findFirst()
                    .orElse(null);

            if (setter == null) {
                // wrong parameter name was provided
                return null;
            }

            try {
                switch (setter.getParameterTypes()[0].getTypeName()) {
                    case "boolean" -> setter.invoke(blankProduct, Boolean.parseBoolean(entry.getValue()));
                    case "double" ->  setter.invoke(blankProduct, Double.parseDouble(entry.getValue()));
                    case "int" -> setter.invoke(blankProduct, Integer.parseInt(entry.getValue()));
                    default -> setter.invoke(blankProduct, entry.getValue());
                }
            } catch (IllegalAccessException | InvocationTargetException e) {
                // wrong parameter value was provided
                return null;
            }

        }

        return blankProduct;
    }

    public static <T extends Product> String getTypedFieldsAsString(Class<T> aClass, String... exceptions) {
        List<Field> fields = new ArrayList<>();
        collectAllFields(aClass, fields);

        return fields.stream()
                .filter(field -> Arrays.stream(exceptions).noneMatch(exception -> exception.equalsIgnoreCase(field.getName())))
                .map(field -> field.getName() + "=" + field.getAnnotatedType())
                .collect(Collectors.joining(", "));
    }
    private static <T extends Product> void collectAllFields(Class<T> aClass, List<Field> allFields) {
        Class<?> superclass = aClass.getSuperclass();
        if (!superclass.equals(Product.class.getSuperclass())) {
            collectAllFields((Class<T>) superclass, allFields);
        }

        allFields.addAll(Arrays.asList(aClass.getDeclaredFields()));
    }
}
