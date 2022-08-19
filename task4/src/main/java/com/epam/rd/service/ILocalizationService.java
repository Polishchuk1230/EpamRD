package com.epam.rd.service;

import java.lang.reflect.Field;

public interface ILocalizationService {

    void setLocale(String localeCode);

    String getLocalizedFieldName(String fieldName);
}
