package com.epam.rd.filter.wrapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

import java.util.Enumeration;
import java.util.Locale;
import java.util.NoSuchElementException;

public class LocalizedRequestWrapper extends HttpServletRequestWrapper {
    private Locale locale;
    private Locale[] locales;

    /**
     * Constructs a request object wrapping the given request.
     *
     * @param request the {@link HttpServletRequest} to be wrapped.
     * @throws IllegalArgumentException if the request is null
     */
    public LocalizedRequestWrapper(HttpServletRequest request, Locale locale, Locale[] locales) {
        super(request);
        this.locale = locale;
        this.locales = locales;
    }

    @Override
    public Locale getLocale() {
        return this.locale;
    }

    @Override
    public Enumeration<Locale> getLocales() {
        return new Enumeration<>() {
            private int counter;

            @Override
            public boolean hasMoreElements() {
                return locales.length > counter;
            }

            @Override
            public Locale nextElement() {
                if (!hasMoreElements()) {
                    throw new NoSuchElementException();
                }
                return locales[counter++];
            }
        };
    }
}
