package com.epam.rd.service.impl;

import com.epam.rd.annotation.ProductField;
import com.epam.rd.service.ILocalizationService;

import java.lang.reflect.Field;
import java.util.*;

public class LocalizationService implements ILocalizationService {
    ResourceBundle resourceBundle;

    public LocalizationService() {
        resourceBundle = loadLocale(""); // set locale by default
    }

    public void setLocale(String localeCode) {
        resourceBundle = loadLocale(localeCode);
    }

    public String getLocalizedFieldName(Field field) {
        return resourceBundle.getString(field.getAnnotation(ProductField.class).value());
    }

    private static ResourceBundle loadLocale(String localeCode) {
        return ResourceBundle.getBundle("locale", new Locale(localeCode));
    }
}
