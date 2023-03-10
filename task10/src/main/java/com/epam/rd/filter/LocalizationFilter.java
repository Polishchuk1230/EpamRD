package com.epam.rd.filter;

import com.epam.rd.wrapper.LocalizedRequestWrapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.jsp.jstl.core.Config;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class LocalizationFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        String currentLocaleStrKey = Config.FMT_LOCALE + ".session";
        List<Locale> availableLocales = Arrays.stream(getInitParameter("locales").split(", ?"))
                .map(this::buildLocale)
                .collect(Collectors.toList());
        Locale currentLocale = null;

        // put paramLocale to a storage
        String paramLocale = req.getParameter("locale");
        if (paramLocale != null && findAppropriateLocale(List.of(buildLocale(paramLocale)), availableLocales) != null) {
            req.getSession().setAttribute(currentLocaleStrKey, req.getParameter("locale"));
        }

        // set locale from a storage
        if (req.getSession().getAttribute(currentLocaleStrKey) != null) {
            String currentLocaleStrValue = String.valueOf(
                    req.getSession().getAttribute(currentLocaleStrKey));
            Locale locale = buildLocale(currentLocaleStrValue);
            currentLocale = findAppropriateLocale(List.of(locale), availableLocales);
        }
        // set locale from user's browser preferences
        if (currentLocale == null) {
            currentLocale = findAppropriateLocale(Collections.list(req.getLocales()), availableLocales);
        }
        // set default locale
        if (currentLocale == null) {
            Locale locale = buildLocale(getInitParameter("defaultLocale"));
            currentLocale = findAppropriateLocale(List.of(locale), availableLocales);
        }

        req.getSession().setAttribute(currentLocaleStrKey, currentLocale);
        HttpServletRequest wrappedRequest = new LocalizedRequestWrapper(req, currentLocale, availableLocales);
        super.doFilter(wrappedRequest, res, chain);
    }

    private Locale findAppropriateLocale(List<Locale> requiredLocales, List<Locale> availableLocales) {
        return requiredLocales.stream()
                .filter(availableLocales::contains)
                .findFirst()
                .orElse(null);
    }

    private Locale buildLocale(String str) {
        String language = str;
        String region = null;

        if (str.contains("_")) {
            language = str.substring(0, str.indexOf("_"));
            region = str.substring(str.indexOf("_") + 1);
        }

        return new Locale.Builder().setLanguage(language).setRegion(region).build();
    }
}