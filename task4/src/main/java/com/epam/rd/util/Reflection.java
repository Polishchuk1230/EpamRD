package com.epam.rd.util;

import com.epam.rd.annotation.ProductField;
import com.epam.rd.context.ApplicationContext;
import com.epam.rd.pojo.Product;
import com.epam.rd.service.ILocalizationService;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

public class Reflection {

    public static <T extends Product> T fillProduct(T blankProduct, Map<String, String> parameters) {
        return fillProduct(blankProduct, parameters, false);
    }

    /**
     * fill the blank Product instance with parameters
     * @param blankProduct
     * @param parameters
     * @return the product instance filled with parameters or null
     * @param <T>
     */
    public static <T extends Product> T fillProduct(T blankProduct, Map<String, String> parameters, boolean isLocalized) {
        Method[] methods = blankProduct.getClass().getMethods();

        // If we receive the localized parameters, here we standardize the key using standardizeParameters() method.
        // Example for the default locale: max weight=90 ==> maxWeight=90
        if (isLocalized) {
            try {
                parameters = standardizeParameters(parameters, blankProduct.getClass());
            } catch (IllegalArgumentException e) {
                return null;
            }
        }

        // Here, for each entry, using reflection mechanisms, we find a setter, parse/cast a value, and invoke the setter with that value
        for (Map.Entry<String, String> entry : parameters.entrySet()) {

            String key = entry.getKey();
            Method setter = Arrays.stream(methods)
                    .filter(method -> method.getName().equalsIgnoreCase("set" + key))
                    .findFirst()
                    .orElse(null);

            if (setter == null) {
                // Somewhy an appropriate setter doesn't exist.
                return null;
            }

            try {
                setter.invoke(blankProduct, switch (setter.getParameterTypes()[0].getTypeName()) {
                    case "boolean" -> Boolean.parseBoolean(entry.getValue());
                    case "double" ->  Double.parseDouble(entry.getValue());
                    case "int" -> Integer.parseInt(entry.getValue());
                    default -> entry.getValue();
                });
            } catch (IllegalAccessException | InvocationTargetException | NumberFormatException e) {
                // Wrong parameter value was provided.
                return null;
            }

        }

        return blankProduct;
    }

    // This method maps the localized user-friendly keys to normal.
    // Example for the default locale: max weight=90 ==> maxWeight=90
    private static Map<String, String> standardizeParameters(Map<String, String> parameters, Class<? extends Product> aClass) {
        ILocalizationService localizationService = (ILocalizationService) ApplicationContext.getInstance().find("localizationService");

        List<Field> fields = new ArrayList<>();
        Reflection.collectAllFields(aClass, fields);

        return parameters.entrySet().stream().collect(Collectors.toMap(
                entry -> fields.stream()
                        .filter(field -> field.isAnnotationPresent(ProductField.class))
                        .filter(field -> Optional.ofNullable(localizationService.getLocalizedFieldName(field))
                                .orElse("").equalsIgnoreCase(entry.getKey()))
                        .findFirst().map(Field::getName).orElseThrow(IllegalArgumentException::new)
                , Map.Entry::getValue));
    }

    /**
     * Return String representation of all fields of a Product
     * @param aClass
     * @param exceptions
     * @return
     * @param <T>
     */
    public static <T extends Product> String getTypedFieldsAsString(Class<T> aClass, String... exceptions) {
        ILocalizationService localizationService = (ILocalizationService) ApplicationContext.getInstance().find("localizationService");

        List<Field> fields = new ArrayList<>();
        collectAllFields(aClass, fields);

        return fields.stream()
                .filter(field -> Arrays.stream(exceptions).noneMatch(exception -> exception.equalsIgnoreCase(field.getName())))
                .map(field -> localizationService.getLocalizedFieldName(field) + "=" + field.getAnnotatedType())
                .collect(Collectors.joining(", "));
    }

    // Recursively collect to a provided list all the fields of each class in the Product hierarchy up to Product.
    public static <T extends Product> void collectAllFields(Class<T> aClass, List<Field> allFields) {
        Class<?> superclass = aClass.getSuperclass();
        if (!superclass.equals(Product.class.getSuperclass())) {
            collectAllFields((Class<T>) superclass, allFields);
        }

        allFields.addAll(Arrays.asList(aClass.getDeclaredFields()));
    }
}
