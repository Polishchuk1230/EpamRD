package com.epam.rd.service.impl;

import com.epam.rd.service.ILocalizationService;

import java.util.Locale;
import java.util.ResourceBundle;

public class LocalizationService implements ILocalizationService {
    ResourceBundle resourceBundle;

    public LocalizationService() {
        resourceBundle = loadLocale(""); // set locale by default
    }

    public void setLocale(String localeCode) {
        resourceBundle = loadLocale(localeCode);
    }

    public String getLocalizedFieldName(String fieldName) {
        return resourceBundle.getString(fieldName);
    }

    private static ResourceBundle loadLocale(String localeCode) {
        return ResourceBundle.getBundle("locale", new Locale(localeCode));
    }
}
