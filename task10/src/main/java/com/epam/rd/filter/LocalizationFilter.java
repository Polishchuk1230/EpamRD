package com.epam.rd.filter;

import com.epam.rd.filter.wrapper.LocalizedRequestWrapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.jsp.jstl.core.Config;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;

public class LocalizationFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        String currentLocaleStrKey = Config.FMT_LOCALE + ".session";
        Locale[] availableLocales = Arrays.stream(getInitParameter("locales").split(", "))
                .map(this::mapLocale)
                .toArray(Locale[]::new);
        Locale currentLocale = null;

        // put paramLocale to a storage
        String paramLocale = req.getParameter("locale");
        if (paramLocale != null && findAppropriateLocale(Collections.enumeration(List.of(mapLocale(paramLocale))), availableLocales) != null) {
            req.getSession().setAttribute(currentLocaleStrKey, req.getParameter("locale"));
        }

        // set locale from a storage
        if (req.getSession().getAttribute(currentLocaleStrKey) != null) {
            String currentLocaleStrValue = String.valueOf(
                    req.getSession().getAttribute(currentLocaleStrKey));
            currentLocale = mapLocale(currentLocaleStrValue);
        }
        // set locale from user's browser preferences
        if (currentLocale == null) {
            currentLocale = findAppropriateLocale(req.getLocales(), availableLocales);
        }
        // set default locale
        if (currentLocale == null) {
            currentLocale = mapLocale(getInitParameter("defaultLocale"));
        }

        req.getSession().setAttribute(currentLocaleStrKey, currentLocale);
        HttpServletRequest wrappedRequest = new LocalizedRequestWrapper(req, currentLocale, availableLocales);
        super.doFilter(wrappedRequest, res, chain);
    }

    private Locale findAppropriateLocale(Enumeration<Locale> requiredLocales, Locale[] availableLocales) {
        while (requiredLocales.hasMoreElements()) {
            Locale requiredLocale = requiredLocales.nextElement();
            for (Locale availableLocale : availableLocales) {
                if (availableLocale.equals(requiredLocale)) {
                    return requiredLocale;
                }
            }
        }
        return null;
    }

    private Locale mapLocale(String str) {
        String language = str;
        String region = null;

        if (str.contains("_")) {
            language = str.substring(0, str.indexOf("_"));
            region = str.substring(str.indexOf("_") + 1);
        }

        return new Locale.Builder().setLanguage(language).setRegion(region).build();
    }
}