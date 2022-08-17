package com.epam.rd.service.impl;

import com.epam.rd.annotation.ProductField;
import com.epam.rd.service.ILocalizationService;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class LocalizationService implements ILocalizationService {
    private Map<String, String> currentLocale;

    public LocalizationService() {
        this("EN"); // set locale by default
    }

    public LocalizationService(String localeCode) {
        currentLocale = loadLocale(localeCode);
    }

    public void setLocale(String localeCode) {
        currentLocale = loadLocale(localeCode);
    }

    public String getLocalizedFieldName(Field field) {
        return currentLocale.get(field.getAnnotation(ProductField.class).value());
    }

    private static Map<String, String> loadLocale(String localeCode) {
        Map<String, String> map = new HashMap<>();

        try (Scanner scanner = new Scanner(new File(String.format("task4/src/main/resources/locales/locale_%s.txt", localeCode)))) {
            while (scanner.hasNextLine()) {
                String temp = scanner.nextLine();
                if (temp.startsWith("#") || !temp.contains("=")) {
                    continue;
                }

                int separatorPosition = temp.indexOf('=');
                map.put(temp.substring(0, separatorPosition), temp.substring(separatorPosition+1));
            }
        } catch (FileNotFoundException e) {
            // If there is no locale-file, do nothing. Just let the map be empty.
        }

        return map;
    }
}
