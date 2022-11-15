package com.epam.rd.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.jsp.jstl.core.Config;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;

public class LocalizationFilterTest {
    private final String AVAILABLE_LOCALES_INIT_PARAMETER = "pl, en,uk";
    private final String CURRENT_LOCALE_ATTRIBUTE_NAME = Config.FMT_LOCALE + ".session";
    private final String CURRENT_LOCALE_VALUE_IN_STORAGE = "uk";
    private final String DEFAULT_LOCALE_INIT_PARAMETER = "en";
    private final String LOCALE_OF_BROWSER_VALUE = "pl";
    private final Enumeration<Locale> LOCALES_OF_BROWSER =
            Collections.enumeration(
                    List.of(
                            new Locale.Builder().setLanguage(LOCALE_OF_BROWSER_VALUE).build()));

    @Test
    public void useLocaleFromStorageTest() throws ServletException, IOException {
        FilterConfig mockedFilterConfig = Mockito.mock(FilterConfig.class);
        Mockito.when(mockedFilterConfig.getInitParameter("locales")).thenReturn(AVAILABLE_LOCALES_INIT_PARAMETER);

        LocalizationFilter localizationFilter = new LocalizationFilter();
        localizationFilter.init(mockedFilterConfig);

        HttpServletRequest mockedRequest = Mockito.mock(HttpServletRequest.class);

        HttpSession mockedSession = Mockito.mock(HttpSession.class);
        Mockito.when(mockedRequest.getSession()).thenReturn(mockedSession);
        // 1. set a locale to the storage
        Mockito.when(mockedSession.getAttribute(CURRENT_LOCALE_ATTRIBUTE_NAME)).thenReturn(CURRENT_LOCALE_VALUE_IN_STORAGE);

        TestFilterChain testFilterChain = new TestFilterChain();
        localizationFilter.doFilter(mockedRequest, Mockito.mock(HttpServletResponse.class), testFilterChain);

        String resultLocale = testFilterChain.getRequest().getLocale().toString();
        Assert.assertEquals(CURRENT_LOCALE_VALUE_IN_STORAGE, resultLocale);
        System.out.println(resultLocale);
    }

    @Test
    public void useLocaleFromBrowserSettingsTest() throws ServletException, IOException {
        // 1. there's no locale settled in the storage

        FilterConfig mockedFilterConfig = Mockito.mock(FilterConfig.class);
        // set available locales of application
        Mockito.when(mockedFilterConfig.getInitParameter("locales")).thenReturn(AVAILABLE_LOCALES_INIT_PARAMETER);

        LocalizationFilter localizationFilter = new LocalizationFilter();
        localizationFilter.init(mockedFilterConfig);

        HttpServletRequest mockedRequest = Mockito.mock(HttpServletRequest.class);

        HttpSession mockedSession = Mockito.mock(HttpSession.class);
        Mockito.when(mockedRequest.getSession()).thenReturn(mockedSession);

        // 2. set empty collection of appropriate locales for a browser
        Mockito.when(mockedRequest.getLocales()).thenReturn(LOCALES_OF_BROWSER);

        TestFilterChain testFilterChain = new TestFilterChain();
        localizationFilter.doFilter(mockedRequest, Mockito.mock(HttpServletResponse.class), testFilterChain);

        String resultLocale = testFilterChain.getRequest().getLocale().toString();
        Assert.assertEquals(LOCALE_OF_BROWSER_VALUE, resultLocale);
        System.out.println(resultLocale);
    }

    @Test
    public void useDefaultLocaleTest() throws ServletException, IOException {
        // 1. there's no locale settled in the storage

        FilterConfig mockedFilterConfig = Mockito.mock(FilterConfig.class);
        // set available locales of application
        Mockito.when(mockedFilterConfig.getInitParameter("locales")).thenReturn(AVAILABLE_LOCALES_INIT_PARAMETER);
        // 3. set a default locale of application
        Mockito.when(mockedFilterConfig.getInitParameter("defaultLocale")).thenReturn(DEFAULT_LOCALE_INIT_PARAMETER);

        LocalizationFilter localizationFilter = new LocalizationFilter();
        localizationFilter.init(mockedFilterConfig);

        HttpServletRequest mockedRequest = Mockito.mock(HttpServletRequest.class);

        HttpSession mockedSession = Mockito.mock(HttpSession.class);
        Mockito.when(mockedRequest.getSession()).thenReturn(mockedSession);

        // 2. set empty collection of appropriate locales for a browser
        Mockito.when(mockedRequest.getLocales()).thenReturn(Collections.emptyEnumeration());

        TestFilterChain testFilterChain = new TestFilterChain();
        localizationFilter.doFilter(mockedRequest, Mockito.mock(HttpServletResponse.class), testFilterChain);

        String resultLocale = testFilterChain.getRequest().getLocale().toString();
        Assert.assertEquals(DEFAULT_LOCALE_INIT_PARAMETER, resultLocale);
        System.out.println(resultLocale);
    }

    /**
     * FilterChain implementation designed to extract ServletRequest instance after processing by the tested filter
     */
    private class TestFilterChain implements FilterChain {
        private ServletRequest request;

        @Override
        public void doFilter(ServletRequest request, ServletResponse response) {
            this.request = request;
        }

        public ServletRequest getRequest() {
            return request;
        }
    }
}
